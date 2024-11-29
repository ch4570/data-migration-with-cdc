package com.example.demo.utils.deserializer

import com.example.demo.event.dto.MessageOutboxPayload
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer
import org.springframework.stereotype.Component


@Component
class MessageOutboxPayloadDeserializer(
    private val objectMapper: ObjectMapper,
) : Deserializer<MessageOutboxPayload> {

    override fun deserialize(topic: String?, data: ByteArray?): MessageOutboxPayload {
        val extractedData = objectMapper.readTree(data)
            .get("payload")
            .get("after").textValue()

        return objectMapper.readValue(extractedData, MessageOutboxPayload::class.java)
    }
}
