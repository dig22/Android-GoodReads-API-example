package com.dig.goodReads.components.books

import com.dig.goodReads.api.model.Book

sealed class BooksState {
    object Startup : BooksState()
    object Loading : BooksState()
    data class Error(val message: String?) : BooksState()
    data class BooksLoaded(val books: List<Book> ) : BooksState()
    object BooksLoadedFromCache : BooksState()
}