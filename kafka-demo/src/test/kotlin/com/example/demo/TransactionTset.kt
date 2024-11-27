package com.example.demo

import com.example.demo.config.MongoConfig
import com.example.demo.entity.EventStatus
import com.example.demo.entity.FileEvent
import com.example.demo.entity.OperationType
import com.example.demo.repository.FileEventRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import

@DataMongoTest
@Import(MongoConfig::class)
//@Transactional
class TransactionTest {

    @Autowired
    private lateinit var fileEventRepository: FileEventRepository


    @Test
    fun `롤백_테스트`() {
        val fileEvent = FileEvent(
            fileId = "잔잘법",
            fileName = "ㅎㅇ",
            extension = "ㅎㅇ",
            operationType = OperationType.CREATE,
            eventStatus = EventStatus.CREATED,
        )

        fileEventRepository.save(fileEvent)

//        throw RuntimeException()
    }
}
