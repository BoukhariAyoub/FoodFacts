package com.boukharist.foodfacts.view

/**
 * Abstract ViewModel State
 */
open class ViewModelState

/**
 * Generic Loading ViewModel State
 */
object LoadingState : ViewModelState()


data class ErrorState(val error: Throwable) : ViewModelState()