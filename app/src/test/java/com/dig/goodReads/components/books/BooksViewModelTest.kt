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
import org.mockito.Mock

class BooksViewModelTest :  BaseTestClass() {

    private lateinit var bookRepository: BookRepository
    private lateinit var booksViewModel: BooksViewModel
   // private lateinit var dataSourceFactory: BooksDataSourceFactory

    @Mock
    private lateinit var dataSourceFactory: BooksDataSourceFactory


    @ExperimentalCoroutinesApi
    @Before
    override fun before() {
        super.before()
        bookRepository = get()
        booksViewModel = get()
        dataSourceFactory = BooksDataSourceFactory(bookRepository)
    }

    @Test
    fun bookSearch_search_is_successful(){
        assert(booksViewModel.booksLiveData.value == BooksState.Startup)
        runBlocking {
             booksViewModel.search(TEST_SEARCH).apply {

             }
            val value = booksViewModel.getMoviesPagedList()?.value
            println("Book Search Tested")
            println(value)
            assert(value.isNullOrEmpty())
        }
    }
}