package com.dig.goodReads.components.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dig.goodReads.data.BooksDataSource
import com.dig.goodReads.data.BooksDataSourceFactory
import com.dig.goodReads.model.Book
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class BooksViewModel(booksDataSourceFactory: BooksDataSourceFactory) : ViewModel() , BooksDataSource.ErrorListener  {

    private val booksPagedList: LiveData<PagedList<Book>>

    private var privateState = MutableLiveData<BooksState>()

    val booksLiveData: LiveData<BooksState>
        get() = privateState

    private val factory : BooksDataSourceFactory = booksDataSourceFactory

    private val config : PagedList.Config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setInitialLoadSizeHint(10)
        .setPageSize(20)
        .setPrefetchDistance(20)
        .build()

    private val executor : ExecutorService = Executors.newFixedThreadPool(5)

    private var searchQueryCache : String  = ""

    init {

        factory.dataSourceErrorListener = this
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
        factory.booksDataSource?.invalidate()
        searchQueryCache = searchQuery
    }

    fun getMoviesPagedList(): LiveData<PagedList<Book>>? {
        return booksPagedList
    }

    override fun onError(error: String) {
        privateState.postValue(BooksState.Error(error))
    }
}