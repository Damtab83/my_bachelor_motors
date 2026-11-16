package com.damtab.my_bachelor_motors.dto.mapper;

import com.damtab.my_bachelor_motors.dto.request.DocumentRequestDto;
import com.damtab.my_bachelor_motors.dto.response.DocumentResponseDto;
import com.damtab.my_bachelor_motors.entity.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {

    public Document toEntity(DocumentRequestDto dto) {
        Document document = new Document();

        document.setName(dto.name());
        document.setMimeType(dto.mimeType());
        document.setContenu(dto.contenu());

        return document;
    }

    public DocumentResponseDto toResponseDto(Document document) {
        return new DocumentResponseDto(
                document.getId(),
                document.getName(),
                document.getMimeType(),
                document.getContenu()
        );

    }
}
