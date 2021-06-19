package com.nelson.testapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseViewModel<A> : ViewModel() {

    private val _action = MutableSharedFlow<A>()
    val action: SharedFlow<A> get() = _action

    init {
        collectActions()
    }

    /**
     * Handle the different actions that [BaseViewModel] can perform
     */
    abstract fun handleAction(action: A)

    /**
     * Set a new action
     */
    fun setAction(action: A) {
        viewModelScope.launch {
            _action.emit(action)
        }
    }

    /**
     * Collect the actions passed into [setAction]
     */
    private fun collectActions() {
        viewModelScope.launch {
            action.collect {
                handleAction(it)
            }
        }
    }
}