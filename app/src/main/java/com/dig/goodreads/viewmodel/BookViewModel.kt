package com.dig.goodreads.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dig.goodreads.api.BookProvider
import com.dig.goodreads.api.book.BookSearchEndPoint
import com.dig.goodreads.model.Book
import com.dig.goodreads.state.BookSearchState
import org.koin.core.KoinComponent

class BookViewModel constructor(private val bookProvider : BookProvider) : ViewModel() , KoinComponent{

    var listOfBooks : ArrayList<Book> = ArrayList()

    private val privateState = MutableLiveData<BookSearchState>()


    val postLiveData : LiveData<BookSearchState>
        get() = privateState

    var searchQueryCached = ""
    private var pageChached : Int = 1

    init {
            privateState.value = BookSearchState.Startup
    }

    fun fetchBooks(searchQuery : String ,page : Int, refresh :Boolean = false) {

        if(searchQueryCached == searchQuery && page == pageChached ){
            privateState.value = BookSearchState.BooksLoaded(listOfBooks,page)
            return
        }

        if(refresh){
            listOfBooks.clear()
        }

        privateState.value = BookSearchState.Loading

        bookProvider.searchBooks(searchQuery,page,object : BookSearchEndPoint.Callback{
            override fun onFetchSuccess(books: List<Book>) {
                listOfBooks.addAll(books)

                privateState.postValue(BookSearchState.BooksLoaded(listOfBooks,page))

                searchQueryCached = searchQuery
                pageChached = page
            }

            override fun onFetchFailed() {
                privateState.postValue(BookSearchState.Error("Error"))
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel", "onCleared() called")
    }
}