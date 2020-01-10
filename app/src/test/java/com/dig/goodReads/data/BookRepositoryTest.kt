package com.dig.goodReads.data

import com.dig.goodReads.*
import com.dig.goodReads.components.books.BooksState
import com.dig.goodReads.components.details.DetailsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
class BookRepositoryTest : BaseTestClass() {

    private lateinit var bookRepository: BookRepository

    @ExperimentalCoroutinesApi
    @Before
    override fun before() {
        super.before()
        bookRepository = BookRepositoryImpl()
    }


    @Test
    fun bookSearch_success() = runBlocking {
        val value =bookRepository.searchBooks(TEST_SEARCH,1)
        assert(value is BooksState.BooksLoaded)
        assert((value as BooksState.BooksLoaded).books.isNotEmpty())
    }

    @Test
    fun fetchBook_details_success() = runBlocking {
        val value =bookRepository.getBookDescription(TEST_BOOK_ID)
        assert(value is DetailsState.DetailsLoaded)
        assert((value as DetailsState.DetailsLoaded).details.isNotEmpty())
    }

    @Test
    fun bookSearch_failsWith_Error() = runBlocking {
        val value =bookRepository.searchBooks(TEST_SEARCH_WRONG,1)
        assert(value is BooksState.Error)
    }

    @Test
    fun fetchBook_details_failWith_Error() = runBlocking {
        val value = bookRepository.getBookDescription(TEST_BOOK_ID_WRONG)
        assert(value is DetailsState.Error)
    }

}