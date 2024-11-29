package com.example.demo.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("message-outbox")
data class MessageOutbox(
    @Id
    val id: String? = null,
    val senderId: Long,
    val content: String,
    val roomId: Long,
    val operationType: OperationType,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val attachments: List<Attachment>
)
