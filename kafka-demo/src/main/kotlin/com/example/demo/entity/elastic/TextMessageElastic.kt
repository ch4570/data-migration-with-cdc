package com.example.demo.entity.elastic

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import java.time.LocalDateTime

@Document(indexName = "text-message-index")
data class TextMessageElastic(
    @Id
    val id: String? = null,
    val messageId: String,
    val senderId: Long,
    val content: String,
    val roomId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
