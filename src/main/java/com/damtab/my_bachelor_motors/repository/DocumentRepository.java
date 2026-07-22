package com.damtab.my_bachelor_motors.repository;

import com.damtab.my_bachelor_motors.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
