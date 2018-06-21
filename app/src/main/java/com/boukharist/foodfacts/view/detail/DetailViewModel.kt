package com.boukharist.foodfacts.view.detail

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.boukharist.foodfacts.base.DisposableViewModel
import com.boukharist.foodfacts.data.datasource.local.ProductEntity
import com.boukharist.foodfacts.data.repository.ProductRepository
import com.boukharist.foodfacts.util.SchedulerProvider
import com.boukharist.foodfacts.view.ErrorState
import com.boukharist.foodfacts.view.LoadingState
import com.boukharist.foodfacts.view.ViewModelState

class DetailViewModel(private val productRepository: ProductRepository,
                      private val schedulerProvider: SchedulerProvider)
    : DisposableViewModel() {
    companion object {
        const val TAG = "DetailViewModel"
    }

    private val _states = MutableLiveData<ViewModelState>()
    val states: LiveData<ViewModelState>
        get() = _states

    @SuppressLint("RxLeakedSubscription")
    fun getProduct(id: String) {
        launch {
            productRepository.getProductById(id)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnSubscribe { _states.postValue(LoadingState) }
                    .subscribe({ product ->
                        _states.postValue(LoadedState(DisplayableItem.from(product)))
                    }, { throwable ->
                        Log.e(TAG, throwable.message, throwable)
                        _states.postValue(ErrorState(throwable))
                    })
        }
    }

    data class LoadedState(val value: DisplayableItem) : ViewModelState()

    data class DisplayableItem(val id: String, val name: String, val pictureUrl: String?, val ingredients: String?, val energy: String?) {
        companion object {
            fun from(productEntity: ProductEntity) = DisplayableItem(
                    productEntity.id,
                    productEntity.name,
                    productEntity.picture,
                    productEntity.ingredients,
                    productEntity.energy
            )
        }
    }

}