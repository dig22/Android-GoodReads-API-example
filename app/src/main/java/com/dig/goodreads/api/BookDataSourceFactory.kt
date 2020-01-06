package com.dig.goodreads.api

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dig.goodreads.api.book.BookSearchEndPoint
import com.dig.goodreads.model.Book


class BookDataSourceFactory : DataSource.Factory<Int, Book> {

    private val mutableLiveData: MutableLiveData<DataSource<Int, Book>>
    var bookDataSource: BookDataSource? = null
    private val bookSearchEndPoint : BookSearchEndPoint

    lateinit var searchQuery : String

    constructor(bookSearchEndPoint: BookSearchEndPoint) : super(){
        this.bookSearchEndPoint = bookSearchEndPoint
        mutableLiveData = MutableLiveData()
    }

    override fun create(): DataSource<Int, Book> {
        bookDataSource =  BookDataSource(bookSearchEndPoint , searchQuery )
        mutableLiveData.postValue(bookDataSource)
        return bookDataSource!!
    }



    fun getMutableLiveData():  MutableLiveData<DataSource<Int, Book>> {
        return mutableLiveData
    }
}