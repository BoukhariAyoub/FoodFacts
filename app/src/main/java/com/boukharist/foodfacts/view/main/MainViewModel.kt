package com.boukharist.foodfacts.view.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.boukharist.foodfacts.base.DisposableViewModel
import com.boukharist.foodfacts.data.repository.ProductRepository
import com.boukharist.foodfacts.util.SchedulerProvider
import com.boukharist.foodfacts.view.main.list.ProductSearchItem

class MainViewModel(private val productRepository: ProductRepository,
                    private val schedulerProvider: SchedulerProvider)
    : DisposableViewModel() {
    companion object {
        const val TAG = "MainViewModel"
    }

    private val _products = MutableLiveData<List<ProductSearchItem>>()
    val products: LiveData<List<ProductSearchItem>>
        get() = _products

    fun getAllProducts() {
        launch {
            productRepository.getAllProducts()
                    .flattenAsObservable { it }
                    .map { ProductSearchItem.from(it) }
                    .toList()
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({ list ->
                        _products.postValue(list)
                    }, { throwable ->
                        //ignored error
                        Log.e(TAG, throwable.message, throwable)
                    })
        }
    }


}