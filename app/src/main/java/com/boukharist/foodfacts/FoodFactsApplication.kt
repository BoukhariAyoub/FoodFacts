package com.boukharist.foodfacts

import android.app.Application
import com.boukharist.foodfacts.di.foodApp
import org.koin.android.ext.android.startKoin

class FoodFactsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin context
        startKoin(this, foodApp, extraProperties = mapOf(Pair("SERVER_URL", "https://world.openfoodfacts.org/")))
    }
}
