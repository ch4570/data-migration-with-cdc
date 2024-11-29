package com.example.demo.entity.elastic

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.*
import java.time.LocalDateTime

@Document(
    indexName = "text-message-index",
    writeTypeHint = WriteTypeHint.FALSE,
)
data class TextMessageElastic(
    @Id
    val id: String? = null,
    val messageId: String,
    val senderId: Long,
    val content: String,
    val roomId: Long,
    @Field(type = FieldType.Date, format = [DateFormat.strict_date_hour_minute_second])
    val createdAt: LocalDateTime,
    @Field(type = FieldType.Date, format = [DateFormat.strict_date_hour_minute_second])
    val updatedAt: LocalDateTime,
)
