package com.example.demo.event.dto

import com.example.demo.entity.EventStatus
import com.example.demo.entity.OperationType
import com.example.demo.entity.mongo.FileContent
import com.example.demo.entity.mongo.FileEvent
import com.example.demo.entity.mongo.FileExtraInfo

data class Attachment(
    val id: Long,
    val contentType: String,
    val content: Content,
) {
    fun convertToFileEvent(operationType: OperationType) : FileEvent {
        val extraInfo = FileExtraInfo(
            thumbnailUrl = this.content.extraInfo.thumbnailUrl,
            smallThumbnailUrl = this.content.extraInfo.smallThumbnailUrl,
            mediumThumbnailUrl = this.content.extraInfo.mediumThumbnailUrl,
            largeThumbnailUrl = this.content.extraInfo.largeThumbnailUrl,
            width = this.content.extraInfo.width,
            height = this.content.extraInfo.height,
        )

        val content = FileContent(
            extraInfo = extraInfo,
            title = this.content.title,
            name = this.content.name,
            filename = this.content.filename,
            type = this.content.type,
            icon = this.content.icon,
            size = this.content.size,
            ext = this.content.ext,
            serverUrl = this.content.serverUrl,
            fileUrl = this.content.fileUrl,
            externalCode = this.content.externalCode,
            externalUrl = this.content.externalUrl,
            externalShared = this.content.externalShared,
            filterType = this.content.filterType,
        )

        return FileEvent(
            contentType = "file",
            content = content,
            operationType = operationType,
            eventStatus = EventStatus.CREATED,
        )
    }
}

data class Content(
    val extraInfo: ExtraInfo,
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

data class ExtraInfo(
    val thumbnailUrl: String? = null,
    val smallThumbnailUrl: String? = null,
    val mediumThumbnailUrl: String? = null,
    val largeThumbnailUrl: String? = null,
    val width: Int? = null,
    val height: Int? = null,
)
