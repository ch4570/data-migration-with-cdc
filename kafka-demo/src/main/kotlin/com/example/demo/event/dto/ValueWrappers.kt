package com.example.demo.event.dto

import com.example.demo.utils.LocalDateTimeDeserializer
import com.example.demo.utils.LocalDateTimeSerializer
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.time.LocalDateTime

class IdWrapper(
    @JsonProperty("\$oid")
    val value: String,
)

class LongTypeWrapper(
    @JsonProperty("\$numberLong")
    val value: Long,
)

class LocalDateTimeWrapper(
    @JsonProperty("\$date")
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    val value: LocalDateTime,
)
