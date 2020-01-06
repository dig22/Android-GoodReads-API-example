package com.dig.goodreads.components.details

import com.dig.goodreads.components.book.BookSearchState
import com.dig.goodreads.model.Book

sealed class DetailsState {
    object Startup : DetailsState()
    object Loading : DetailsState()
    data class Error(val message: String?) : DetailsState()
    data class BooksLoaded(val details: String) : DetailsState()
}