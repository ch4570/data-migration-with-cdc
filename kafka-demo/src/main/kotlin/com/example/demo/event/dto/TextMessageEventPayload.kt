package com.example.demo.event.dto

import com.example.demo.entity.EventStatus
import com.example.demo.entity.OperationType
import java.time.LocalDateTime

data class TextMessageEventPayload(
    val id: String,
    val messageId: String,
    val senderId: Long,
    val content: String,
    val roomId: Long,
    val operationType: OperationType,
    val eventStatus: EventStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
