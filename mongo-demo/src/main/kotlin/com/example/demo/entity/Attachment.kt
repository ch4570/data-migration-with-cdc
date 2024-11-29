package com.example.demo.entity

data class Attachment(
    val fileId: String,
    val fileName: String,
    val extension: String,
    val operationType: OperationType
)
