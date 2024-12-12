package com.example.demo.entity.mongo

import com.example.demo.entity.EventStatus
import com.example.demo.entity.OperationType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("file-event")
data class FileEvent(
    @Id
    val _id: String? = null,
    val contentType: String,
    val content: FileContent,
    val operationType: OperationType,
    val eventStatus: EventStatus,
)

data class FileContent(
    val extraInfo: FileExtraInfo,
    val title: String,
    val name: String,
    val filename: String,
    val type: String,
    val icon: String,
    val size: Int,
    val ext: String,
    val serverUrl: String,
    val fileUrl: String,
    val externalCode: String? = null,
    val externalUrl: String? = null,
    val externalShared: Boolean,
    val filterType: String
)

data class FileExtraInfo(
    val thumbnailUrl: String? = null,
    val smallThumbnailUrl: String? = null,
    val mediumThumbnailUrl: String? = null,
    val largeThumbnailUrl: String? = null,
    val width: Int? = null,
    val height: Int? = null,
)

