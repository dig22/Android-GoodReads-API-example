package com.dig.goodreads.components.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dig.goodreads.model.Book
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class BookViewModel : ViewModel , BookDataSource.ErrorListner  {

    private var booksPagedList: LiveData<PagedList<Book>>? = null

    private var privateState = MutableLiveData<BooksState>()

    val publicState: LiveData<BooksState>
        get() = privateState

    val factory : BookDataSourceFactory

    val config : PagedList.Config

    val executor : ExecutorService

    var searchQueryCache : String  = ""


    constructor(bookDataSourceFactory: BookDataSourceFactory) : super(){
        this.factory = bookDataSourceFactory

         config =  PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(20)
            .setPrefetchDistance(20)
            .build()

        executor = Executors.newFixedThreadPool(5)
        factory.dataSourceErrorListner = this
        booksPagedList = LivePagedListBuilder<Int, Book>(factory,config)
            .setFetchExecutor(executor)
            .build()

        privateState.postValue(BooksState.Startup)

    }

    fun search(searchQuery :String) {

        privateState.postValue(BooksState.Loading)


        if(searchQuery == searchQueryCache){
            privateState.postValue(BooksState.BooksLoadedFromCache)
            return
        }

        factory.searchQuery = searchQuery
        factory.bookDataSource?.invalidate();
        searchQueryCache = searchQuery
    }

    fun getMoviesPagedList(): LiveData<PagedList<Book>>? {
        return booksPagedList
    }

    override fun onError(error: String) {
        privateState.postValue(BooksState.Error(error))
    }
}