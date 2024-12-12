package com.example.demo.utils

inline fun <T> Collection<T>.isNotEmpty(action : (collection: Collection<T>) -> Unit) =
    action(this)
