package com.dig.goodreads

import com.dig.goodreads.api.BookRepository
import com.dig.goodreads.components.book.BooksDataSourceFactory
import com.dig.goodreads.components.book.BooksState
import com.dig.goodreads.components.book.BooksViewModel
import com.nhaarman.mockitokotlin2.verifyBlocking

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.any
import org.junit.Before
import org.junit.Test
import org.koin.test.get
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.anyString
import org.mockito.Mockito.verify

class BooksViewModelTest :  BaseTestClass() {

    private lateinit var bookRepository: BookRepository
    private lateinit var booksViewModel: BooksViewModel
    private lateinit var dataSourceFactory: BooksDataSourceFactory

    @ExperimentalCoroutinesApi
    @Before
    override fun before() {
        super.before()
        bookRepository = get()
        booksViewModel = get()
        dataSourceFactory = get()
    }

    @Test
    fun bookSearch_search_is_successful(){
        assert(booksViewModel.booksLiveData.value == BooksState.Startup)
        booksViewModel.search(TEST_SEARCH)
        return runBlocking {
            val value = booksViewModel.booksLiveData.value
               when(value){
                is BooksState.Loading ->{
                    print("Tested Search")
                }
            }
        }

    }
}