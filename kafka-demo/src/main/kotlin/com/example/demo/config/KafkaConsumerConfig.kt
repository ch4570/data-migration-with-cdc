package com.example.demo.config

import com.example.demo.event.dto.MessageEventPayload
import com.example.demo.event.dto.SingleFileEventPayload
import com.example.demo.event.dto.TextMessageEventPayload
import com.example.demo.utils.MessageEventPayloadDeserializer
import com.example.demo.utils.SingleFileEventPayloadDeserializer
import com.example.demo.utils.TextMessageEventPayloadDeserializer
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
class KafkaConsumerConfig(
    // Custom Deserializer 등록
    private val messageEventPayloadDeserializer: MessageEventPayloadDeserializer,
    private val textMessageEventPayloadDeserializer: TextMessageEventPayloadDeserializer,
    private val singleFileEventPayloadDeserializer: SingleFileEventPayloadDeserializer,
) {

    @Bean(name = ["message-event-dlt-consumer"])
    fun messageEventDLTConsumer() : ConcurrentKafkaListenerContainerFactory<String, MessageEventPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, MessageEventPayload>()
        factory.consumerFactory = messageEventDLTConsumerFactory()

        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }

    private fun messageEventDLTConsumerFactory() : ConsumerFactory<String, MessageEventPayload> {
        return DefaultKafkaConsumerFactory(createConsumerConfigs(), StringDeserializer(), ErrorHandlingDeserializer(JsonDeserializer()))
    }

    @Bean(name = ["text-message-event-dlt-consumer"])
    fun textMessageEventDLTConsumer() : ConcurrentKafkaListenerContainerFactory<String, TextMessageEventPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, TextMessageEventPayload>()
        factory.consumerFactory = textMessageEventDLTConsumerFactory()

        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }

    private fun textMessageEventDLTConsumerFactory() : ConsumerFactory<String, TextMessageEventPayload> {
        return DefaultKafkaConsumerFactory(createConsumerConfigs(), StringDeserializer(), ErrorHandlingDeserializer(JsonDeserializer()))
    }

    @Bean(name = ["single-file-event-dlt-consumer"])
    fun singleFileEventDLTConsumer() : ConcurrentKafkaListenerContainerFactory<String, SingleFileEventPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, SingleFileEventPayload>()
        factory.consumerFactory = singleFileEventDLTConsumerFactory()

        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }

    private fun singleFileEventDLTConsumerFactory() : ConsumerFactory<String, SingleFileEventPayload> {
        return DefaultKafkaConsumerFactory(createConsumerConfigs(), StringDeserializer(), ErrorHandlingDeserializer(JsonDeserializer()))
    }

    @Bean(name = ["message-event-consumer"])
    fun messageEventConsumer(
        @Qualifier("message-event-errorhandler")
        handler: DefaultErrorHandler,
    ) : ConcurrentKafkaListenerContainerFactory<String, MessageEventPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, MessageEventPayload>()
        factory.consumerFactory = messageEventConsumerFactory()
        factory.setCommonErrorHandler(handler)
        // Auto-Commit을 적용하지 않고 수동 커밋 활성화
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }

    private fun messageEventConsumerFactory() : ConsumerFactory<String, MessageEventPayload> {
        // ErrorHandlingDeserializer 사용으로 무한 에러 로그 생성 문제를 차단함
        val deserializer = ErrorHandlingDeserializer(messageEventPayloadDeserializer)

        return DefaultKafkaConsumerFactory(createConsumerConfigs(), StringDeserializer(), deserializer)
    }

    @Bean(name = ["text-message-event-consumer"])
    fun textMessageEventConsumer(
        @Qualifier("text-message-event-errorhandler")
        handler: DefaultErrorHandler,
    ) : ConcurrentKafkaListenerContainerFactory<String, TextMessageEventPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, TextMessageEventPayload>()
        factory.consumerFactory = textMessageEventConsumerFactory()
        factory.setCommonErrorHandler(handler)
        // Auto-Commit을 적용하지 않고 수동 커밋 활성화
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }

    private fun textMessageEventConsumerFactory() : ConsumerFactory<String, TextMessageEventPayload> {
        // ErrorHandlingDeserializer 사용으로 무한 에러 로그 생성 문제를 차단함
        val deserializer = ErrorHandlingDeserializer(textMessageEventPayloadDeserializer)

        return DefaultKafkaConsumerFactory(createConsumerConfigs(), StringDeserializer(), deserializer)
    }

    @Bean(name = ["single-file-event-consumer"])
    fun singleFileEventConsumer(
        @Qualifier("single-file-event-errorhandler")
        handler: DefaultErrorHandler,
    ) : ConcurrentKafkaListenerContainerFactory<String, SingleFileEventPayload> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, SingleFileEventPayload>()
        factory.consumerFactory = singleFileEventConsumerFactory()
        factory.setCommonErrorHandler(handler)
        // Auto-Commit을 적용하지 않고 수동 커밋 활성화
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }

    private fun singleFileEventConsumerFactory() : ConsumerFactory<String, SingleFileEventPayload> {
        // ErrorHandlingDeserializer 사용으로 무한 에러 로그 생성 문제를 차단함
        val deserializer = ErrorHandlingDeserializer(singleFileEventPayloadDeserializer)

        return DefaultKafkaConsumerFactory(createConsumerConfigs(), StringDeserializer(), deserializer)
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
