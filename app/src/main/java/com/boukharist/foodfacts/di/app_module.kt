package com.boukharist.foodfacts.di

import android.arch.persistence.room.Room
import com.boukharist.foodfacts.data.datasource.local.FoodFactsDatabase
import com.boukharist.foodfacts.data.repository.ProductRepository
import com.boukharist.foodfacts.data.repository.ProductRepositoryImpl
import com.boukharist.foodfacts.util.AppScheduleProvider
import com.boukharist.foodfacts.util.SchedulerProvider
import com.boukharist.foodfacts.view.detail.DetailViewModel
import com.boukharist.foodfacts.view.main.MainActivity
import com.boukharist.foodfacts.view.main.MainViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

val appModule = applicationContext {

    viewModel { DetailViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get()) }

    //Repository
    bean { ProductRepositoryImpl(get(), get()) as ProductRepository }

    // Schedulers
    bean { AppScheduleProvider() as SchedulerProvider }

    // Database
    bean {
        Room.databaseBuilder(androidApplication(), FoodFactsDatabase::class.java, "food-db")
                .fallbackToDestructiveMigration()
                .build()
    }

    bean { get<FoodFactsDatabase>().productDao() }
}

val foodApp = listOf(appModule, remoteDatasourceModule)
