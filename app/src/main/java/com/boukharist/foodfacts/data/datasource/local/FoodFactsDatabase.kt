package com.boukharist.foodfacts.data.datasource.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [ProductEntity::class], version = 2)
abstract class FoodFactsDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}