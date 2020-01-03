package com.dig.goodreads.state

import com.dig.goodreads.model.Book

sealed class BookSearchState{
    object Startup : BookSearchState()
    object Loading : BookSearchState()
    data class Error(val message: String?) : BookSearchState()
    data class BooksLoaded(val books: List<Book>, val page : Int) : BookSearchState()
}