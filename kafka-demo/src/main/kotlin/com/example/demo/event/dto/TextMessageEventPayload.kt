package com.example.demo.event.dto

import com.example.demo.entity.EventStatus
import com.example.demo.entity.OperationType
import com.fasterxml.jackson.annotation.JsonProperty

data class TextMessageEventPayload(
    @JsonProperty("_id")
    val id: IdWrapper,
    val messageId: String,
    val senderId: LongTypeWrapper,
    val content: String,
    val roomId: LongTypeWrapper,
    val operationType: OperationType,
    val createdAt: LocalDateTimeWrapper,
    val updatedAt: LocalDateTimeWrapper,
)
