//package com.example.demo.event
//
//import com.example.demo.event.dto.MessageEventPayload
//import com.example.demo.service.usecase.RegisterMessageEventUseCase
//import org.apache.kafka.clients.consumer.ConsumerRecord
//import org.slf4j.LoggerFactory
//import org.springframework.kafka.annotation.KafkaListener
//import org.springframework.kafka.support.Acknowledgment
//import org.springframework.scheduling.annotation.Async
//import org.springframework.stereotype.Component
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//
//@Component
//class MessageEventListener(
//    private val registerMessageUseCase: RegisterMessageEventUseCase,
//) {
//
//    private val logger = LoggerFactory.getLogger(MessageEventListener::class.java)
//
//    @Async
//    @KafkaListener(topics = ["message-event"], groupId = "consumer-group", containerFactory = "message-event-consumer")
//    fun handleEvent(record: ConsumerRecord<String, MessageEventPayload>, acknowledgment: Acknowledgment) {
//        logger.info("메시지 수신 완료 = [${record.value()}], 수신 시각 = [${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}]")
////        throw Exception()
//        registerMessageUseCase.registerMessageEvent(record.value())
//        acknowledgment.acknowledge()
//    }
//}
