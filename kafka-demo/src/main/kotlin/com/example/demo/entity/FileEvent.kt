package com.example.demo.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("file-event")
data class FileEvent(
    @Id
    val id: String? = null,
    val fileId: String,
    val fileName: String,
    val extension: String,
    val operationType: OperationType,
    val eventStatus: EventStatus,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
