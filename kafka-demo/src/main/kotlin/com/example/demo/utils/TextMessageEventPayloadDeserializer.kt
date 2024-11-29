package com.example.demo.utils

import com.example.demo.event.dto.TextMessageEventPayload
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer
import org.springframework.stereotype.Component


@Component
class TextMessageEventPayloadDeserializer(
    private val objectMapper: ObjectMapper,
) : Deserializer<TextMessageEventPayload> {

    override fun deserialize(topic: String?, data: ByteArray?): TextMessageEventPayload {
        val extractedData = objectMapper.readTree(data)
            .get("payload")
            .get("after").textValue()

        return objectMapper.readValue(extractedData, TextMessageEventPayload::class.java)
    }
}
