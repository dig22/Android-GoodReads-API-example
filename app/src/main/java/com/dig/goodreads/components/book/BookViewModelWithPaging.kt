package com.dig.goodreads.components.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dig.goodreads.api.BookDataSourceFactory
import com.dig.goodreads.model.Book
import java.util.concurrent.Executors

class BookViewModelWithPaging : ViewModel  {

   // private val movieDataSourceLiveData = MutableLiveData<BookDataSource>()

   private var booksPagedList: LiveData<PagedList<Book>>? = null

    constructor(factory: BookDataSourceFactory) : super(){

       val config =  PagedList.Config.Builder()
           .setEnablePlaceholders(true)
           .setInitialLoadSizeHint(10)
           .setPageSize(20)
           .setPrefetchDistance(4)
           .build()

        val executor = Executors.newFixedThreadPool(5)

        booksPagedList = LivePagedListBuilder<Int, Book>(factory,config)
            .setFetchExecutor(executor)
            .build()

    }

    fun getMoviesPagedList(): LiveData<PagedList<Book>>? {
        return booksPagedList
    }
}