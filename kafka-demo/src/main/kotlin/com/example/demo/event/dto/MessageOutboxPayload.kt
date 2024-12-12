package com.example.demo.event.dto

import com.example.demo.entity.OperationType
import com.fasterxml.jackson.annotation.JsonProperty

data class MessageOutboxPayload(
    @JsonProperty("_id")
    val id: IdWrapper,
    val senderId: LongTypeWrapper,
    val content: String,
    val roomId: LongTypeWrapper,
    val operationType: OperationType,
    val attachments: List<Attachment>,
)

//data class Attachment(
//    val fileId: String,
//    val fileName: String,
//    val extension: String,
//    val operationType: OperationType,
//)
