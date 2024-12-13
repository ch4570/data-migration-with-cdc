package com.example.demo.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

inline fun <T> Collection<T>.isNotEmpty(action : (collection: Collection<T>) -> Unit) =
    action(this)

fun Map<String, Any?>.getLongValue(key: String) = (get(key) as Int?)?.toLong()
fun Map<String, Any?>.getStringValue(key: String) = (get(key) as String?)

@Suppress("UNCHECKED_CAST")
fun <T> Map<String, Any?>.getGenericList(key: String) = (get(key) as? MutableList<T>) ?: mutableListOf()

@Suppress("UNCHECKED_CAST")
fun Map<String, Any?>.getLocalDateTimeUnwrappedValue(key: String) : LocalDateTime? {
    val unixTimeStamp = ((get(key) as? Map<String, Any?>)?.get("\$date")) as? Long

    return unixTimeStamp?.let {
        Instant.ofEpochSecond(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }
}

