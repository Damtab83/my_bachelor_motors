package com.damtab.my_bachelor_motors.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String mimeType;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] contenu;
}
