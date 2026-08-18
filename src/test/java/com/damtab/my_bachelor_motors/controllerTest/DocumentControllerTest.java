package com.damtab.my_bachelor_motors.controllerTest;

import com.damtab.my_bachelor_motors.configuration.jwt.JwtUtils;
import com.damtab.my_bachelor_motors.entity.Document;
import com.damtab.my_bachelor_motors.service.DocumentService;
import com.damtab.my_bachelor_motors.service.UserCustomDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(Document.class)
@AutoConfigureMockMvc(addFilters = false)
public class DocumentControllerTest {

    @MockitoBean
    private JwtUtils jwtUtils;

    @MockitoBean
    private UserCustomDetailsService userCustomDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DocumentService documentService;




    @Test
    public void uploadDocument_shouldReturn201() throws Exception {

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.pdf",
                "application/pdf",
                "%PDF-1.4 test".getBytes()
        );

        Document document = new Document();
        document.setId(1L);
        document.setName("test.pdf");
        document.setMimeType("application/pdf");

        Mockito.when(documentService.uploadFile(any()))
                .thenReturn(document);

        mockMvc.perform(
                        multipart("/api/document/upload")
                                .file(file)
                )
                .andExpect(status().isCreated());

        Mockito.verify(documentService)
                .uploadFile(any());
    }

    @Test
    public void downloadDocument_shouldReturnPdf() throws Exception {

        Document document = new Document();
        document.setId(44L);
        document.setName("fichier_pdf.pdf");
        document.setMimeType("application/pdf");
        document.setContenu("%PDF-1.4 test".getBytes());

        Mockito.when(documentService.getDocument(44L))
                .thenReturn(document);

        mockMvc.perform(get("/api/document/44/download"))
                .andExpect(status().isOk())
                .andExpect(header().string(
                        "Content-Disposition",
                        "attachment; filename=\"fichier_pdf.pdf\""))
                .andExpect(content().contentType("application/pdf"));
    }
}
