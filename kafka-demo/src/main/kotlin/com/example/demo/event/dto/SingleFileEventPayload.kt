package com.example.demo.event.dto

import com.example.demo.entity.OperationType
import com.fasterxml.jackson.annotation.JsonProperty

data class SingleFileEventPayload(
    @JsonProperty("_id")
    val id: IdWrapper,
    val fileId: String,
    val fileName: String,
    val extension: String,
    val operationType: OperationType,
)
