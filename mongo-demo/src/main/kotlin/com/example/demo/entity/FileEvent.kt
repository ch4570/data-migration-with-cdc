package com.example.demo.entity

import java.time.LocalDateTime

data class FileEvent(
    val fileId: String,
    val fileName: String,
    val extension: String,
    val operationType: OperationType,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
