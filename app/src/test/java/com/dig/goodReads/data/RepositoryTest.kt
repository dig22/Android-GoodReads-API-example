package com.dig.goodReads.data

import com.dig.goodReads.*
import com.dig.goodReads.components.books.BooksState
import com.dig.goodReads.components.details.DetailsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RepositoryTest : BaseTestClass() {

    lateinit var bookRepository: BookRepository

    @ExperimentalCoroutinesApi
    @Before
    override fun before() {
        super.before()
        bookRepository = BookRepositoryImpl()
    }


    @Test
    fun bookSearch_success() = runBlocking {
        val value =bookRepository.searchBooksNew(TEST_SEARCH,1)
        when(value ){
            is BooksState.BooksLoaded ->{
                assert(value.books.isNotEmpty())
            }
            else ->{
                assert(false)
            }
        }
    }

    @Test
    fun fetchBook_details_success() = runBlocking {
        val value =bookRepository.getBookDescription(TEST_BOOK_ID)
        when(value ){
            is DetailsState.DetailsLoaded ->{
                assert(value.details.isNotEmpty())
            }
            else ->{
                assert(false)
            }
        }
    }

    @Test
    fun bookSearch_fail() = runBlocking {
        val value =bookRepository.searchBooksNew(TEST_SEARCH_WRONG,1)
        when(value ){
            is BooksState.Error ->{
                assert(true)
            }
            else ->{
                assert(false)
            }
        }
    }

    @Test
    fun fetchBook_details_fail() = runBlocking {
        val value = bookRepository.getBookDescription(TEST_BOOK_ID_WRONG)
        when(value ){
            is DetailsState.Error ->{
                assert(true)
            }
            else ->{
                assert(false)
            }
        }
    }

}