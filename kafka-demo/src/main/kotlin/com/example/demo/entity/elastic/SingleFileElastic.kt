package com.example.demo.entity.elastic

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.*
import java.time.LocalDateTime

@Document(
    indexName = "single-file-index",
    writeTypeHint = WriteTypeHint.FALSE
)
data class SingleFileElastic(
    @Id
    val id: String? = null,
    val fileId: String,
    val fileName: String,
    val extension: String,
    @Field(type = FieldType.Date, format = [DateFormat.strict_date_hour_minute_second])
    val createdAt: LocalDateTime,
    @Field(type = FieldType.Date, format = [DateFormat.strict_date_hour_minute_second])
    val updatedAt: LocalDateTime,
)
