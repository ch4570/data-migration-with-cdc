package com.example.demo.utils

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime>() {
    override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonParser.longValue), ZoneId.systemDefault())
    }
}
