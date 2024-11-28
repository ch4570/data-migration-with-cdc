package com.example.demo.entity.elastic

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import java.time.LocalDateTime

@Document(indexName = "single-file-index")
data class SingleFileElastic(
    @Id
    val id: String? = null,
    val fileId: String,
    val fileName: String,
    val extension: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
