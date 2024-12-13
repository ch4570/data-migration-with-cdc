package com.example.demo.utils

import org.bson.Document
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Suppress("UNCHECKED_CAST")
fun Document.getObjectMap(propertyName: String) : Map<String, Any?> = get(propertyName) as? Map<String, Any?> ?: mapOf()

fun Document.getNestedObjectMap(nestedPropertyName: String) : Map<String, Any?> {
    val nameTokens = nestedPropertyName.split(".")
    var result: Map<String, Any?> = mapOf()

    @Suppress("UNCHECKED_CAST")
    nameTokens.forEach {
        result = if (result.isEmpty()) getObjectMap(it) else result[it] as? Map<String, Any?> ?: mapOf()
    }

    return result
}

fun <T> Document.getGenericCollection(propertyName: String) : Collection<T> {
    @Suppress("UNCHECKED_CAST")
    return get(propertyName) as? Collection<T> ?: emptyList()
}

@Suppress("UNCHECKED_CAST")
fun <T> Document.getNestedGenericCollection(nestedPropertyName: String) : Collection<T> {
    val nameTokens = nestedPropertyName.split(".")
    var objectMap: Map<String, Any?> = mapOf()

    nameTokens.subList(0, nameTokens.size - 1)
        .forEach {
            objectMap = if (objectMap.isEmpty()) getObjectMap(it) else objectMap[it] as Map<String, Any?>
        }

    return objectMap[nameTokens.last()] as? Collection<T> ?: emptyList()
}

fun Document.getNullableIntValue(propertyName: String) : Int? {
    return (get(propertyName) as? Int)
}

fun Document.getNullableNestedIntValue(nestedPropertyName: String) : Int? {
    val nameTokens = nestedPropertyName.split(".")
    var objectMap: Map<String, Any?> = mapOf()

    nameTokens.subList(0, nameTokens.size - 1)
        .forEach {
            @Suppress("UNCHECKED_CAST")
            objectMap = if (objectMap.isEmpty()) getObjectMap(it) else objectMap[it] as Map<String, Any?>
        }
    return (objectMap[nameTokens.last()] as? Int)
}

fun Document.getNullableLongValue(propertyName: String) = getNullableIntValue(propertyName)?.toLong()
fun Document.getNullableNestedLongValue(nestedPropertyName: String) = getNullableNestedIntValue(nestedPropertyName)?.toLong()

fun Document.getLocalDateTimeValue(propertyName: String) : LocalDateTime {
    return getDateValue(propertyName)
        .toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
}

fun Document.getDateValue(propertyName: String) = get(propertyName) as Date

fun Document.getNestedNullableStringValue(nestedPropertyName: String) : String? {
    val nameTokens = nestedPropertyName.split(".")
    var objectMap: Map<String, Any?> = mapOf()

    nameTokens.subList(0, nameTokens.size - 1)
        .forEach {
            @Suppress("UNCHECKED_CAST")
            objectMap = if (objectMap.isEmpty()) getObjectMap(it) else objectMap[it] as Map<String, Any?>
        }

    return objectMap[nameTokens.last()] as? String
}


