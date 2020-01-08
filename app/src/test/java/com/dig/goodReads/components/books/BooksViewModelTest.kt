package com.dig.goodReads.components.books

import com.dig.goodReads.BaseTestClass
import com.dig.goodReads.TEST_SEARCH
import com.dig.goodReads.data.BookRepository
import com.dig.goodReads.data.BooksDataSourceFactory

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