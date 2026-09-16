package com.damtab.my_bachelor_motors.controller;

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

    //Get All PDF Documents
    @GetMapping
    public ResponseEntity<Object> getAllDocument() {
        List<Document> myListDocument = documentService.getAllDocuments();
        return ResponseEntity.status(HttpStatus.OK).body(myListDocument);
    }

    //Get Document By ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDocumentById(@PathVariable Long id) {
        Document doc = documentService.getDocument(id);
        return doc != null ? ResponseEntity.status(HttpStatus.OK).body(doc) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
