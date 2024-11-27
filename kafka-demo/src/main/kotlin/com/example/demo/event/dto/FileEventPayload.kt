package com.example.demo.event.dto

import com.example.demo.entity.OperationType
import java.time.LocalDateTime

data class FileEventPayload(
    val id: String,
    val fileId: String,
    val fileName: String,
    val extension: String,
    val operationType: OperationType,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
