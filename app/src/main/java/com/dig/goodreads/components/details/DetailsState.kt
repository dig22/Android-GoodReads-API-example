package com.dig.goodreads.components.details

sealed class DetailsState {
    object Startup : DetailsState()
    object Loading : DetailsState()
    data class Error(val message: String?) : DetailsState()
    data class DetailsLoaded(val details: String) : DetailsState()
}