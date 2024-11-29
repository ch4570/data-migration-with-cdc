package com.example.demo.config

import com.example.demo.event.dto.MessageEventPayload
import com.example.demo.event.dto.SingleFileEventPayload
import com.example.demo.event.dto.TextMessageEventPayload
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.ConsumerRecordRecoverer
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.util.backoff.FixedBackOff

@Configuration(proxyBeanMethods = false)
class KafkaErrorHandlerConfig {

    @Bean(name = ["message-event-errorhandler"])
    fun messageEventErrorHandler(kafkaTemplate : KafkaTemplate<String, MessageEventPayload>) : DefaultErrorHandler {
        val recover = ConsumerRecordRecoverer { record, exception ->
            kafkaTemplate.send(
                "message-event.DLT",
                record.key() as String,
                record.value() as MessageEventPayload
            )

            exception.printStackTrace()
        }

        return DefaultErrorHandler(recover, FixedBackOff(5000L, 4))
    }

    @Bean(name = ["text-message-event-errorhandler"])
    fun textMessageEventErrorHandler(kafkaTemplate : KafkaTemplate<String, TextMessageEventPayload>) : DefaultErrorHandler {
        val recover = ConsumerRecordRecoverer { record, exception ->
            kafkaTemplate.send(
                "text-message-event.DLT",
                record.key() as String,
                record.value() as TextMessageEventPayload
            )

            exception.printStackTrace()
        }

        return DefaultErrorHandler(recover, FixedBackOff(5000L, 4))
    }

    @Bean(name = ["single-file-event-errorhandler"])
    fun singleFileEventErrorHandler(kafkaTemplate : KafkaTemplate<String, SingleFileEventPayload>) : DefaultErrorHandler {
        val recover = ConsumerRecordRecoverer { record, exception ->
            kafkaTemplate.send(
                "file-event.DLT",
                record.key() as String,
                record.value() as SingleFileEventPayload
            )

            exception.printStackTrace()
        }

        return DefaultErrorHandler(recover, FixedBackOff(5000L, 4))
    }
}
