package com.example.demo.utils

import com.example.demo.event.dto.MessageEventPayload
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer
import org.springframework.stereotype.Component


@Component
class MessageEventPayloadDeserializer(
    private val objectMapper: ObjectMapper,
) : Deserializer<MessageEventPayload> {

    override fun deserialize(topic: String?, data: ByteArray?): MessageEventPayload {
        val extractedData = objectMapper.readTree(data)
            .get("payload")
            .get("after").textValue()

        return objectMapper.readValue(extractedData, MessageEventPayload::class.java)
    }
}
