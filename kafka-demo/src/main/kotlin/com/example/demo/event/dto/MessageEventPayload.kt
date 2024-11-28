package com.example.demo.event.dto

import com.example.demo.entity.EventStatus
import com.example.demo.entity.OperationType
import com.fasterxml.jackson.annotation.JsonProperty

data class MessageEventPayload(
    @JsonProperty("_id")
    val id: IdWrapper,
    val senderId: LongTypeWrapper,
    val content: String,
    val roomId: LongTypeWrapper,
    val operationType: OperationType,
    val eventStatus: EventStatus,
    val createdAt: LocalDateTimeWrapper,
    val updatedAt: LocalDateTimeWrapper,
    val attachments: List<Attachment>,
) {
    fun hasFiles() = attachments.isNotEmpty()
}

class Attachment(
    val fileId: String,
    val fileName: String,
    val extension: String,
    val operationType: OperationType,
    val createdAt: LocalDateTimeWrapper,
    val updatedAt: LocalDateTimeWrapper,
)
