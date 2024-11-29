package com.example.demo.config

import com.example.demo.event.dto.MessageEventPayload
import com.example.demo.event.dto.MessageOutboxPayload
import com.example.demo.event.dto.SingleFileEventPayload
import com.example.demo.event.dto.TextMessageEventPayload
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration(proxyBeanMethods = false)
class KafkaProducerConfig {

    @Bean(name = ["message-outbox-template"])
    fun messageOutboxTemplate() : KafkaTemplate<String, MessageOutboxPayload>
            = KafkaTemplate(
        DefaultKafkaProducerFactory(producerConfigs(), StringSerializer(), JsonSerializer())
    )

    @Bean(name = ["message-event-template"])
    fun messageEventTemplate() : KafkaTemplate<String, MessageEventPayload>
    = KafkaTemplate(
        DefaultKafkaProducerFactory(producerConfigs(), StringSerializer(), JsonSerializer())
    )

    @Bean(name = ["text-message-event-template"])
    fun textMessageEventTemplate() : KafkaTemplate<String, TextMessageEventPayload>
    = KafkaTemplate(
        DefaultKafkaProducerFactory(producerConfigs(), StringSerializer(), JsonSerializer())
    )

    @Bean(name = ["single-file-event-template"])
    fun singleFileEventTemplate() : KafkaTemplate<String, SingleFileEventPayload>
    = KafkaTemplate(
        DefaultKafkaProducerFactory(producerConfigs(), StringSerializer(), JsonSerializer())
    )

    private fun producerConfigs() = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
    )
}
