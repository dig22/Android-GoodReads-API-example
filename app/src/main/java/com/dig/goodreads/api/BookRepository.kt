package com.dig.goodreads.api

import com.dig.goodreads.components.book.BooksState
import com.dig.goodreads.components.details.DetailsState

interface BookRepository {
    suspend fun getBookDescription(bookId: Int) : DetailsState
    suspend fun searchBooksNew(searchString : String,page : Int): BooksState
}