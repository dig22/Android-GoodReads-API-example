package com.dig.goodReads.components.books

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dig.goodReads.BaseTestClass
import com.dig.goodReads.BookRepositoryImplTD
import com.dig.goodReads.TEST_ERROR
import com.dig.goodReads.TEST_SEARCH
import com.dig.goodReads.data.BookRepository
import com.dig.goodReads.data.BooksDataSource
import com.dig.goodReads.data.BooksDataSourceFactory
import com.dig.goodReads.model.Book
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.test.get
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*

class BooksViewModelTest :  BaseTestClass() {

    private lateinit var bookRepository: BookRepository
    private lateinit var booksViewModel: BooksViewModel
   // private lateinit var dataSourceFactory: BooksDataSourceFactory

    @Mock
    private lateinit var dataSourceFactory: BooksDataSourceFactory
    @Mock
    private lateinit var dataSource : BooksDataSource

    @ExperimentalCoroutinesApi
    @Before
    override fun before() {
        super.before()
        bookRepository = get()

        `when`(dataSourceFactory.booksDataSource).thenReturn(dataSource)
        booksViewModel = BooksViewModel(dataSourceFactory)

    }

    private fun setError(){
        (bookRepository as BookRepositoryImplTD).isError = true
    }

    @Test
    fun bookSearch_sets_ErrorListener()= runBlocking{
        setError()
        booksViewModel.search(TEST_SEARCH,true).join()
        verify(dataSourceFactory).dataSourceErrorListener = booksViewModel
    }

    @Test
    fun bookPanelist_is_initialized_onStartup(){
        assert(booksViewModel.booksPagedList != null )
    }

    @Test
    fun bookSearch_search_is_successful() = runBlocking{
        assert(booksViewModel.booksLiveData.value == BooksState.Startup)
        booksViewModel.search(TEST_SEARCH,true).join()
        verify(dataSource, times(1)).invalidate()
        verify(dataSourceFactory).searchQuery = TEST_SEARCH
    }

    @Test
    fun bookSearch_searchWasCached_is_successful() = runBlocking{
        assert(booksViewModel.booksLiveData.value == BooksState.Startup)

        booksViewModel.search(TEST_SEARCH).join()
        booksViewModel.search(TEST_SEARCH).join()

        verify(dataSource,times(1)).invalidate()

    }

    @Test
    fun bookSearch_searchWasNotCached_is_successful() = runBlocking{
        assert(booksViewModel.booksLiveData.value == BooksState.Startup)
        booksViewModel.search(TEST_SEARCH).join()
        booksViewModel.search(TEST_SEARCH+"DON'T CACHE").join()
        verify(dataSource,times(2)).invalidate()
    }

    @Test
    fun bookViewModel_PostsErrorStatus(){
        booksViewModel.onError(TEST_ERROR)
        assert(booksViewModel.booksLiveData.value is BooksState.Error)
    }
}