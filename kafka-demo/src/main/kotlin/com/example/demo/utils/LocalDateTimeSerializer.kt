package com.example.demo.utils

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.time.LocalDateTime
import java.time.ZoneId

class LocalDateTimeSerializer : JsonSerializer<LocalDateTime>() {

    override fun serialize(value: LocalDateTime,
                           gen: JsonGenerator?,
                           serializers: SerializerProvider?
    ) {
        gen?.writeNumber(value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
    }
}
