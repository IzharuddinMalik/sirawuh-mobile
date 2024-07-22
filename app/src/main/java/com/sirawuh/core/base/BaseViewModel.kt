package com.sirawuh.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading
    private val _transparentLoading = MutableStateFlow(false)
    val transparentLoading: StateFlow<Boolean> = _transparentLoading
    private val _isLoadingInProgress = MutableStateFlow(false)
    val isLoadingInProgress: StateFlow<Boolean> = _isLoadingInProgress

    init {
        viewModelScope.launch {
            combine(_loading, _transparentLoading) { absolute, transparent ->
                _isLoadingInProgress.update { absolute || transparent }
            }.collect()
        }
    }

    fun showLoading() {
        _loading.update { true }
    }

    fun showTransparentLoading() {
        _transparentLoading.update { true }
    }

    fun dismissLoading() {
        _loading.update { false }
    }

    fun dismissTransparentLoading() {
        _transparentLoading.update { false }
    }

    fun runInBackground(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            block()
        }
    }
}