package com.test.myweather.shared

interface LocalStorage {
    fun isCached(): Boolean
    fun <T> save(obj: T)
}