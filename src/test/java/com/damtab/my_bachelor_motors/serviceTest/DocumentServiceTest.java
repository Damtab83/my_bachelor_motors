package com.damtab.my_bachelor_motors.serviceTest;

import com.damtab.my_bachelor_motors.entity.Document;
import com.damtab.my_bachelor_motors.entity.ImageCar;
import com.damtab.my_bachelor_motors.repository.DocumentRepository;
import com.damtab.my_bachelor_motors.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentService documentService;

    private Document testDocument;

    @BeforeEach
    public void setup() {

        testDocument = new Document();

        testDocument.setId(44L);
        testDocument.setName("fichier_pdf");
        testDocument.setMimeType("application/pdf");
        testDocument.setContenu("%PDF-1.4 test".getBytes());
    }

    @Test
    public void getAllDocument_shouldReturnList() {

        Mockito.when(documentRepository.findAll()).thenReturn(List.of(testDocument));

        List<Document> result = documentService.getAllDocuments();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(44L, result.get(0).getId());
        assertEquals("fichier_pdf", result.get(0).getName());
        assertEquals("application/pdf", result.get(0).getMimeType());
        assertArrayEquals("%PDF-1.4 test".getBytes(), result.get(0).getContenu());
    }

    @Test
    public void getDocumentById_shouldReturn_whenExists() {

        Mockito.when(documentRepository.findById(44L)).thenReturn(Optional.of(testDocument));

        Document result = documentService.getDocument(44L);
        assertNotNull(result);
        assertEquals(44L, result.getId());
        assertEquals("fichier_pdf", result.getName());
        assertEquals("application/pdf", result.getMimeType());
        assertArrayEquals("%PDF-1.4 test".getBytes(), result.getContenu());
    }

    @Test
    public void uploadFile_shouldSavePdf() throws Exception {


        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.pdf",
                "application/pdf",
                "%PDF-1.4 fichier test".getBytes()
        );


        Mockito.when(documentRepository.save(Mockito.any(Document.class)))
                .thenReturn(testDocument);


        Document result = documentService.uploadFile(file);


        assertNotNull(result);
        assertEquals("fichier_pdf", result.getName());
        assertEquals("application/pdf", result.getMimeType());


        Mockito.verify(documentRepository)
                .save(Mockito.any(Document.class));
    }

    @Test
    public void uploadFile_shouldRejectNonPdf() {


        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "bonjour".getBytes()
        );


        assertThrows(IllegalArgumentException.class, () ->
                documentService.uploadFile(file)
        );
    }

}
