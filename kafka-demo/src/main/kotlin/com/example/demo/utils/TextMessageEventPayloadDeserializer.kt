package com.example.demo.utils

import com.example.demo.event.dto.TextMessageEventPayload
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class TextMessageEventPayloadDeserializer(
    private val objectMapper: ObjectMapper,
) : Deserializer<TextMessageEventPayload> {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun deserialize(topic: String?, data: ByteArray?): TextMessageEventPayload {
        logger.info("이벤트 수신")

        val extractedData = objectMapper.readTree(data)
            .get("payload")
            .get("after").textValue()

        logger.info(extractedData)

        return objectMapper.readValue(extractedData, TextMessageEventPayload::class.java)
    }
}
