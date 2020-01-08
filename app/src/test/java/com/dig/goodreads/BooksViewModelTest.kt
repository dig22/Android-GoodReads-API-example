package com.dig.goodReads

import com.dig.goodReads.api.BookRepository
import com.dig.goodReads.components.books.BooksDataSourceFactory
import com.dig.goodReads.components.books.BooksState
import com.dig.goodReads.components.books.BooksViewModel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.test.get

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
               when( booksViewModel.booksLiveData.value){
                is BooksState.Loading ->{
                    print("Tested Search")
                }
            }
        }

    }
}