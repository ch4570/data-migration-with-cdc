package com.example.demo.config

import com.example.demo.event.dto.MessageEventPayload
import com.example.demo.event.dto.SingleFileEventPayload
import com.example.demo.event.dto.TextMessageEventPayload
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration(proxyBeanMethods = false)
class KafkaConsumerConfig {

    @Bean(name = ["message-event-consumer"])
    fun messageEventConsumer() : ConcurrentKafkaListenerContainerFactory<String, MessageEventPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, MessageEventPayload>()
        factory.consumerFactory = messageEventConsumerFactory()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }

    private fun messageEventConsumerFactory() : ConsumerFactory<String, MessageEventPayload> {
        val deserializer = JsonDeserializer(MessageEventPayload::class.java, false)

        return DefaultKafkaConsumerFactory(createConsumerConfigs(), StringDeserializer(), deserializer)
    }

    @Bean(name = ["text-message-event-consumer"])
    fun textMessageEventConsumer() : ConcurrentKafkaListenerContainerFactory<String, TextMessageEventPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, TextMessageEventPayload>()
        factory.consumerFactory = textMessageEventConsumerFactory()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }

    private fun textMessageEventConsumerFactory() : ConsumerFactory<String, TextMessageEventPayload> {
        val deserializer = JsonDeserializer(TextMessageEventPayload::class.java, false)

        return DefaultKafkaConsumerFactory(createConsumerConfigs(), StringDeserializer(), deserializer)
    }

    @Bean(name = ["single-file-event-consumer"])
    fun singleFileEventConsumer() : ConcurrentKafkaListenerContainerFactory<String, SingleFileEventPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, SingleFileEventPayload>()
        factory.consumerFactory = singleFileEventConsumerFactory()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }

    private fun singleFileEventConsumerFactory() : ConsumerFactory<String, SingleFileEventPayload> {
        val deserializer = JsonDeserializer(SingleFileEventPayload::class.java, false)

        return DefaultKafkaConsumerFactory(createConsumerConfigs(), StringDeserializer(), deserializer)
    }



    private fun createConsumerConfigs() = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
        ConsumerConfig.GROUP_ID_CONFIG to "group-example-1",
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class::java,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
        JsonDeserializer.TRUSTED_PACKAGES to "*",
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to "false",
    )
}
