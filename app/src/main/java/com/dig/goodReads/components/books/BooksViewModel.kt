package com.dig.goodReads.components.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dig.goodReads.data.BooksDataSource
import com.dig.goodReads.data.BooksDataSourceFactory
import com.dig.goodReads.helper.CoroutineViewModel
import com.dig.goodReads.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BooksViewModel(booksDataSourceFactory: BooksDataSourceFactory,
                     uiContext: CoroutineContext = Dispatchers.Main) : CoroutineViewModel(uiContext) , BooksDataSource.ErrorListener  {

    var booksPagedList: LiveData<PagedList<Book>>? = null

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

    private var searchQueryCache : String  = ""

    init {

        factory.dataSourceErrorListener = this
        booksPagedList = LivePagedListBuilder<Int, Book>(factory,config)
            .build()

        privateState.postValue(BooksState.Startup)
    }

    fun search(searchQuery :String, refresh : Boolean = false) = launch {

        privateState.postValue(BooksState.Loading)

        if(searchQuery == searchQueryCache && !refresh){
            privateState.postValue(BooksState.BooksLoadedFromCache)
        }else{
            factory.searchQuery = searchQuery
            factory.booksDataSource?.invalidate()
            searchQueryCache = searchQuery
        }
    }

    override fun onError(error: String) {
        privateState.postValue(BooksState.Error(error))
    }
}