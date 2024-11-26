package com.example.demo.dto

data class BaseResponse<T>(
    val data: T,
    val status: Int
) {

    companion object {
        fun <T> ok(data: T) = BaseResponse(data, 200)
    }
}
