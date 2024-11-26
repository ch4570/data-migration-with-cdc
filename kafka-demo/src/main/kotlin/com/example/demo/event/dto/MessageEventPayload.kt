package com.example.demo.event.dto

import com.example.demo.entity.OperationType
import java.time.LocalDateTime

data class MessageEventPayload(
    val id: String,
    val senderId: Long,
    val content: String,
    val roomId: Long,
    val operationType: OperationType,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val attachments: List<FileEventPayload>
) {
    fun hasFiles() = attachments.isNotEmpty()
}
