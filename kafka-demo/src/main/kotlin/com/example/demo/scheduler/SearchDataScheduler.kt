package com.example.demo.scheduler

import com.example.demo.service.usecase.RegisterMessageSearchDataUseCase
import com.example.demo.utils.findMessageEventIsNotCompleted
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class SearchDataScheduler(
    private val mongoTemplate: MongoTemplate,
    private val registerMessageSearchDataUseCase: RegisterMessageSearchDataUseCase,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(fixedRate = 5000)
    fun registerMessageSearchDataTask() {
        logger.info("메시지 이벤트 ES 전송 시작")
        val documents = mongoTemplate.findMessageEventIsNotCompleted(10, "message-event")
        documents.forEach { registerMessageSearchDataUseCase.saveMessageData(it) }
        logger.info("메시지 이벤트 ES 전송 종료")
    }
}
