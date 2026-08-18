package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.entity.Document;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    public List<Document> getAllDocuments() {
        List<Document> myListDocuments = documentRepository.findAll();
        if(myListDocuments.isEmpty()) {
            throw new ResourceNotFoundException("Aucuns documents trouvés");
        }
        return myListDocuments;
    }

    public Document getDocument(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aucun document trouvé"));
    }

    public Document uploadFile(MultipartFile newFile) throws IOException {

        if (!"application/pdf".equals(newFile.getContentType())) {
            throw new IllegalArgumentException("Le fichier doit être un PDF.");
        }

        String filename = newFile.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Extension invalide.");
        }

        byte[] bytes = newFile.getBytes();

        if (bytes.length < 4 ||
                bytes[0] != '%' ||
                bytes[1] != 'P' ||
                bytes[2] != 'D' ||
                bytes[3] != 'F') {

            throw new IllegalArgumentException("Le fichier n'est pas un PDF valide.");
        }

        Document doc = new Document();
        doc.setName(newFile.getOriginalFilename());
        doc.setMimeType(newFile.getContentType());
        doc.setContenu(newFile.getBytes());

        return documentRepository.save(doc);
    }
}
