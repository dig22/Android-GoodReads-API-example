package com.dig.goodReads.components.details

import com.dig.goodReads.data.BookRepository
import kotlinx.coroutines.runBlocking

class DetailsFetchUseCase(private val bookRepository : BookRepository) {
    suspend fun getBookDescription(bookId : Int) : DetailsState {
        return bookRepository.getBookDescription(bookId)
    }
}