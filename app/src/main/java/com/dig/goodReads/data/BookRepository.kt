package com.dig.goodReads.data

import com.dig.goodReads.components.books.BooksState
import com.dig.goodReads.components.bookDetails.BookDetailsState

interface BookRepository {
    suspend fun getBookDescription(bookId: Int) : BookDetailsState
    suspend fun searchBooks(searchString : String, page : Int): BooksState
}