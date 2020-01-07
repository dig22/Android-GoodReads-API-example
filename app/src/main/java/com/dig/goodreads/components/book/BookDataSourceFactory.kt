package com.dig.goodreads.components.book

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dig.goodreads.api.BookRepository
import com.dig.goodreads.model.Book


class BookDataSourceFactory : DataSource.Factory<Int, Book> {

    private val mutableLiveData: MutableLiveData<DataSource<Int, Book>>
    var bookDataSource: BookDataSource? = null
    private val bookRepository : BookRepository

    var searchQuery : String = "Game"

    var dataSourceErrorListner : BookDataSource.ErrorListner? = null


    constructor(bookRepository: BookRepository) : super(){
        this.bookRepository = bookRepository
        mutableLiveData = MutableLiveData()
    }

    override fun create(): DataSource<Int, Book> {
        bookDataSource = BookDataSource(
            bookRepository,
            searchQuery,
            dataSourceErrorListner
        )
        mutableLiveData.postValue(bookDataSource)
        return bookDataSource!!
    }



    fun getMutableLiveData():  MutableLiveData<DataSource<Int, Book>> {
        return mutableLiveData
    }
}