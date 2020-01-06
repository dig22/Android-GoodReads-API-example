package com.dig.goodreads.api

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dig.goodreads.api.book.BookSearchEndPoint
import com.dig.goodreads.model.Book


class BookDataSourceFactory : DataSource.Factory<Int, Book> {

    private val mutableLiveData: MutableLiveData<BookDataSource>
    private var bookDataSource: BookDataSource? = null
    private val bookSearchEndPoint : BookSearchEndPoint

    constructor(bookSearchEndPoint: BookSearchEndPoint) : super(){
        this.bookSearchEndPoint = bookSearchEndPoint
        mutableLiveData = MutableLiveData()
    }

    override fun create(): DataSource<Int, Book> {
        bookDataSource =  BookDataSource(bookSearchEndPoint)
        mutableLiveData.postValue(bookDataSource)
        return bookDataSource!!
    }

    fun getMutableLiveData(): MutableLiveData<BookDataSource> {
        return mutableLiveData
    }
}