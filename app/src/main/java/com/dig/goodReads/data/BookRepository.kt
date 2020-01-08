package com.dig.goodReads.data

import com.dig.goodReads.components.books.BooksState
import com.dig.goodReads.components.details.DetailsState

interface BookRepository {
    suspend fun getBookDescription(bookId: Int) : DetailsState
    suspend fun searchBooksNew(searchString : String,page : Int): BooksState
}