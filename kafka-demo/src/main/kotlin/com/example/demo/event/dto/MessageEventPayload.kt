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
    val attachments: List<InnerAttachment>,
) {
    fun hasFiles() = attachments.isNotEmpty()
}

class InnerAttachment(
    val fileId: String,
    val fileName: String,
    val extension: String,
    val operationType: OperationType,
)
