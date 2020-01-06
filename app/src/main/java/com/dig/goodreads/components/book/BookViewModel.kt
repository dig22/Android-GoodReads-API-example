package com.dig.goodreads.components.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dig.goodreads.api.BookDataSource
import com.dig.goodreads.api.BookDataSourceFactory
import com.dig.goodreads.api.book.BookSearchEndPoint
import com.dig.goodreads.model.Book
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class BookViewModel : ViewModel   {

    private var booksPagedList: LiveData<PagedList<Book>>? = null

    val factory : BookDataSourceFactory

    val config : PagedList.Config

    val executor : ExecutorService

    constructor(bookDataSourceFactory: BookDataSourceFactory) : super(){
        this.factory = bookDataSourceFactory
         config =  PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(20)
            .setPrefetchDistance(20)
            .build()

         executor = Executors.newFixedThreadPool(5)

        booksPagedList = LivePagedListBuilder<Int, Book>(factory,config)
            .setFetchExecutor(executor)
            .build()

    }

    fun search(searchQuery :String) {
        factory.searchQuery = searchQuery
        factory.bookDataSource?.invalidate();
    }

    fun getMoviesPagedList(): LiveData<PagedList<Book>>? {
        return booksPagedList
    }
}