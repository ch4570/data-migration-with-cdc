package com.example.demo.config

import com.example.demo.event.dto.MessageEventPayload
import com.example.demo.event.dto.SingleFileEventPayload
import com.example.demo.event.dto.TextMessageEventPayload
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration(proxyBeanMethods = false)
class KafkaProducerConfig {

    @Bean(name = ["message-event-producer-factory"])
    fun messageEventProducerFactory() : DefaultKafkaProducerFactory<String, MessageEventPayload> =
        DefaultKafkaProducerFactory(producerConfigs(), StringSerializer(), JsonSerializer())

    @Bean(name = ["message-event-template"])
    fun messageEventTemplate(
        @Qualifier("message-event-producer-factory")
        factory: DefaultKafkaProducerFactory<String, MessageEventPayload>,
    ) : KafkaTemplate<String, MessageEventPayload> = KafkaTemplate(factory)

    @Bean(name = ["text-message-event-producer-factory"])
    fun textMessageEventProducerFactory() : DefaultKafkaProducerFactory<String, TextMessageEventPayload> =
        DefaultKafkaProducerFactory(producerConfigs())

    @Bean(name = ["text-message-event-template"])
    fun textMessageEventTemplate(
        @Qualifier("text-message-event-producer-factory")
        factory: DefaultKafkaProducerFactory<String, TextMessageEventPayload>,
    ) : KafkaTemplate<String, TextMessageEventPayload> = KafkaTemplate(factory)

    @Bean(name = ["single-file-event-producer-factory"])
    fun singleFileEventProducerFactory() : DefaultKafkaProducerFactory<String, SingleFileEventPayload> =
        DefaultKafkaProducerFactory(producerConfigs())

    @Bean(name = ["single-file-event-template"])
    fun singleFileEventTemplate(
        @Qualifier("single-file-event-producer-factory")
        factory: DefaultKafkaProducerFactory<String, SingleFileEventPayload>,
    ) : KafkaTemplate<String, SingleFileEventPayload> = KafkaTemplate(factory)

    private fun producerConfigs() = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
    )
}
