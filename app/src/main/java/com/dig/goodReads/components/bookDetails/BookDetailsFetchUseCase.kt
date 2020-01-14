package com.dig.goodReads.components.bookDetails

import com.dig.goodReads.data.BookRepository

class BookDetailsFetchUseCase(private val bookRepository : BookRepository) {
    suspend fun getBookDescription(bookId : Int) : BookDetailsState {
        return bookRepository.getBookDescription(bookId)
    }
}