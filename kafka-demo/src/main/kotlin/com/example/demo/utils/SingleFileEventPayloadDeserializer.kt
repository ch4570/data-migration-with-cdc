package com.example.demo.utils

import com.example.demo.event.dto.SingleFileEventPayload
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer
import org.springframework.stereotype.Component


@Component
class SingleFileEventPayloadDeserializer(
    private val objectMapper: ObjectMapper,
) : Deserializer<SingleFileEventPayload> {

    override fun deserialize(topic: String?, data: ByteArray?): SingleFileEventPayload {
        val extractedData = objectMapper.readTree(data)
            .get("payload")
            .get("after").textValue()

        return objectMapper.readValue(extractedData, SingleFileEventPayload::class.java)
    }
}
