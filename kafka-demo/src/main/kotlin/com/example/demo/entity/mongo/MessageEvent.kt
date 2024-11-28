package com.example.demo.entity.mongo

import com.example.demo.entity.EventStatus
import com.example.demo.entity.OperationType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("message-event")
data class MessageEvent(
    @Id
    val id: String? = null,
    val senderId: Long,
    val content: String,
    val roomId: Long,
    val operationType: OperationType,
    val eventStatus: EventStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val attachments: List<FileEvent>
)
