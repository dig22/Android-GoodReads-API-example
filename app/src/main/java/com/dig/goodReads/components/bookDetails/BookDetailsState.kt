package com.dig.goodReads.components.bookDetails

sealed class BookDetailsState {
    object Startup : BookDetailsState()
    object Loading : BookDetailsState()
    data class Error(val message: String?) : BookDetailsState()
    data class BookDetailsLoaded(val details: String) : BookDetailsState()
    data class BookDetailsLoadedFromCache(val details: String) : BookDetailsState()
}