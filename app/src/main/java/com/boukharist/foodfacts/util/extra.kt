package com.boukharist.foodfacts.util

import android.app.Activity

/**
 * Retrieve extra from Activity intent
 */
inline fun <reified T> Activity.extra(key: String) =
        lazy { intent.extras[key] as? T ?: error("Intent Argument $key is missing") }

/**
 * Retrieve Optional Argument from Activity's Intent
 */
inline fun <reified T> Activity.optionalExtra(key: String) = lazy {
    if (intent.hasExtra(key)) {
        intent.extras[key] as T
    } else {
        null
    }
}

/**
 * get Tag for any Object
 */
fun Any.getTag(): String = this.javaClass.simpleName