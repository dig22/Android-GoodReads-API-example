package com.dig.goodreads.components.book

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dig.goodreads.api.BookRepository
import com.dig.goodreads.model.Book


class BooksDataSourceFactory : DataSource.Factory<Int, Book> {

    private val mutableLiveData: MutableLiveData<DataSource<Int, Book>>
    var booksDataSource: BooksDataSource? = null
    private val bookRepository : BookRepository

    var searchQuery : String = "Game"

    var dataSourceErrorListner : BooksDataSource.ErrorListner? = null


    constructor(bookRepository: BookRepository) : super(){
        this.bookRepository = bookRepository
        mutableLiveData = MutableLiveData()
    }

    override fun create(): DataSource<Int, Book> {
        booksDataSource = BooksDataSource(
            bookRepository,
            searchQuery,
            dataSourceErrorListner
        )
        mutableLiveData.postValue(booksDataSource)
        return booksDataSource!!
    }



    fun getMutableLiveData():  MutableLiveData<DataSource<Int, Book>> {
        return mutableLiveData
    }
}