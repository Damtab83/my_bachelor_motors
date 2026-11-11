package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.dto.mapper.DocumentMapper;
import com.damtab.my_bachelor_motors.dto.response.DocumentResponseDto;
import com.damtab.my_bachelor_motors.entity.Document;
import com.damtab.my_bachelor_motors.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_DOCUMENT)
public class DocumentController {

    //Differents routing for..
    @Autowired
    private DocumentService documentService;
    @Autowired
    private DocumentMapper documentMapper;

    public DocumentController(DocumentService documentService, DocumentMapper documentMapper) {
        this.documentService = documentService;
        this.documentMapper = documentMapper;
    }

    //Get All PDF Documents
    @GetMapping
    public ResponseEntity<List<DocumentResponseDto>> getAllDocument() {
        List<DocumentResponseDto> myListDocument = documentService.getAllDocuments()
                .stream()
                .map(documentMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(myListDocument);
    }

    //Get Document By ID
    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponseDto> getDocumentById(@PathVariable Long id) {
        Document doc = documentService.getDocument(id);
        if (doc == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(documentMapper.toResponseDto(doc));

    }

    //Download Document By Id
    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        Document doc = documentService.getDocument(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + doc.getName() + "\"")
                .contentType(MediaType.parseMediaType(doc.getMimeType()))
                .body(doc.getContenu());
    }

    //Upload Document for Buy or Rent Car by the User
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) throws IOException {
        documentService.uploadFile(file);
        return ResponseEntity.ok("Document enregistré");
    }
}
