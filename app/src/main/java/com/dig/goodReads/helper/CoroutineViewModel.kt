package com.dig.goodReads.helper

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

open class CoroutineViewModel(private val uiContext: CoroutineContext) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = uiContext + job

    private val job = SupervisorJob()

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }
}