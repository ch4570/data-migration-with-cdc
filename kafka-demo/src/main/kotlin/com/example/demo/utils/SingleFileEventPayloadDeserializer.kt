package com.example.demo.utils

import com.example.demo.event.dto.SingleFileEventPayload
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class SingleFileEventPayloadDeserializer(
    private val objectMapper: ObjectMapper,
) : Deserializer<SingleFileEventPayload> {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun deserialize(topic: String?, data: ByteArray?): SingleFileEventPayload {
        logger.info("이벤트 수신")

        val extractedData = objectMapper.readTree(data)
            .get("payload")
            .get("after").textValue()

        logger.info(extractedData)

        return objectMapper.readValue(extractedData, SingleFileEventPayload::class.java)
    }
}
