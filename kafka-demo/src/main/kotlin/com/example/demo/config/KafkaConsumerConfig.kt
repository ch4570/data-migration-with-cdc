package com.example.demo.config

import com.example.demo.event.dto.MessageEventPayload
import com.example.demo.event.dto.MessageOutboxPayload
import com.example.demo.event.dto.SingleFileEventPayload
import com.example.demo.event.dto.TextMessageEventPayload
import com.example.demo.utils.deserializer.MessageEventPayloadDeserializer
import com.example.demo.utils.deserializer.MessageOutboxPayloadDeserializer
import com.example.demo.utils.deserializer.SingleFileEventPayloadDeserializer
import com.example.demo.utils.deserializer.TextMessageEventPayloadDeserializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration(proxyBeanMethods = false)
class KafkaConsumerConfig {

    @Bean(name = ["message-outbox-dlt-consumer"])
    fun messageOutboxDLTConsumer() : ConcurrentKafkaListenerContainerFactory<String, MessageOutboxPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, MessageOutboxPayload>()
        factory.consumerFactory = DefaultKafkaConsumerFactory(
            createConsumerConfigs(), StringDeserializer(), ErrorHandlingDeserializer(JsonDeserializer())
        )

        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }

    @Bean(name = ["message-event-dlt-consumer"])
    fun messageEventDLTConsumer() : ConcurrentKafkaListenerContainerFactory<String, MessageEventPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, MessageEventPayload>()
        factory.consumerFactory = DefaultKafkaConsumerFactory(
            createConsumerConfigs(), StringDeserializer(), ErrorHandlingDeserializer(JsonDeserializer())
        )

        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }

    @Bean(name = ["text-message-event-dlt-consumer"])
    fun textMessageEventDLTConsumer() : ConcurrentKafkaListenerContainerFactory<String, TextMessageEventPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, TextMessageEventPayload>()
        factory.consumerFactory = DefaultKafkaConsumerFactory(
            createConsumerConfigs(), StringDeserializer(), ErrorHandlingDeserializer(JsonDeserializer())
        )

        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }

    @Bean(name = ["single-file-event-dlt-consumer"])
    fun singleFileEventDLTConsumer() : ConcurrentKafkaListenerContainerFactory<String, SingleFileEventPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, SingleFileEventPayload>()
        factory.consumerFactory = DefaultKafkaConsumerFactory(
            createConsumerConfigs(), StringDeserializer(), ErrorHandlingDeserializer(JsonDeserializer())
        )

        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }

    @Bean(name = ["message-outbox-consumer"])
    fun messageOutboxConsumer(
        @Qualifier("message-outbox-errorhandler")
        handler: DefaultErrorHandler,
        deserializer: MessageOutboxPayloadDeserializer,
    ) : ConcurrentKafkaListenerContainerFactory<String, MessageOutboxPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, MessageOutboxPayload>()
        factory.consumerFactory = DefaultKafkaConsumerFactory(
            createConsumerConfigs(), StringDeserializer(), ErrorHandlingDeserializer(deserializer)
        )

        // Auto-Commit을 적용하지 않고 수동 커밋 활성화
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL

        // ErrorHandler 지정
        factory.setCommonErrorHandler(handler)
        return factory
    }

    @Bean(name = ["message-event-consumer"])
    fun messageEventConsumer(
        @Qualifier("message-event-errorhandler")
        handler: DefaultErrorHandler,
        deserializer: MessageEventPayloadDeserializer,
    ) : ConcurrentKafkaListenerContainerFactory<String, MessageEventPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, MessageEventPayload>()
        factory.consumerFactory = DefaultKafkaConsumerFactory(
            createConsumerConfigs(), StringDeserializer(), ErrorHandlingDeserializer(deserializer)
        )

        // Auto-Commit을 적용하지 않고 수동 커밋 활성화
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL

        // ErrorHandler 지정
        factory.setCommonErrorHandler(handler)
        return factory
    }

    @Bean(name = ["text-message-event-consumer"])
    fun textMessageEventConsumer(
        @Qualifier("text-message-event-errorhandler")
        handler: DefaultErrorHandler,
        deserializer: TextMessageEventPayloadDeserializer,
    ) : ConcurrentKafkaListenerContainerFactory<String, TextMessageEventPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, TextMessageEventPayload>()
        factory.consumerFactory = DefaultKafkaConsumerFactory(
            createConsumerConfigs(), StringDeserializer(), ErrorHandlingDeserializer(deserializer)
        )

        // Auto-Commit을 적용하지 않고 수동 커밋 활성화
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL

        // ErrorHandler 지정
        factory.setCommonErrorHandler(handler)
        return factory
    }

    @Bean(name = ["single-file-event-consumer"])
    fun singleFileEventConsumer(
        @Qualifier("single-file-event-errorhandler")
        handler: DefaultErrorHandler,
        deserializer: SingleFileEventPayloadDeserializer,
    ) : ConcurrentKafkaListenerContainerFactory<String, SingleFileEventPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, SingleFileEventPayload>()
        factory.consumerFactory = DefaultKafkaConsumerFactory(
            createConsumerConfigs(), StringDeserializer(), ErrorHandlingDeserializer(deserializer)
        )

        // Auto-Commit을 적용하지 않고 수동 커밋 활성화
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL

        // ErrorHandler 지정
        factory.setCommonErrorHandler(handler)

        return factory
    }



    private fun createConsumerConfigs() = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class::java,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to ErrorHandlingDeserializer::class.java,
        ConsumerConfig.GROUP_ID_CONFIG to "consumer-group",
        JsonDeserializer.TRUSTED_PACKAGES to "*",
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to "false",
    )
}
