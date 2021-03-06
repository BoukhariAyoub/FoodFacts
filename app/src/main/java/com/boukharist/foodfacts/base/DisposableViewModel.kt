package com.boukharist.foodfacts.base

import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class DisposableViewModel : ViewModel() {

    private val disposables = CompositeDisposable()

    fun launch(disposable: () -> Disposable) {
        disposables.add(disposable())
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}