package com.boukharist.foodfacts.util

class ProductNotFoundException(override val message: String?) : Throwable(message)