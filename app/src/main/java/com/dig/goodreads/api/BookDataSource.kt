package com.dig.goodreads.api

import android.app.Application
import androidx.paging.PageKeyedDataSource
import com.dig.goodreads.api.book.BookDetailEndPoint
import com.dig.goodreads.api.book.BookSearchEndPoint
import com.dig.goodreads.model.Book

class BookDataSource (val bookSearchEndPoint: BookSearchEndPoint, var searchQuery : String) :  PageKeyedDataSource<Int, Book>() {

   // var searchQuery : String = "test"

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Book>
    ) {

        bookSearchEndPoint.searchBooks(searchQuery,1, object : BookSearchEndPoint.Callback{
            override fun onFetchSuccess(books: List<Book>) {
                callback.onResult(books,null,2)
            }
            override fun onFetchFailed() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {
        bookSearchEndPoint.searchBooks(searchQuery,params.key, object : BookSearchEndPoint.Callback{
            override fun onFetchSuccess(books: List<Book>) {
                callback.onResult(books,params.key+1)
            }
            override fun onFetchFailed() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}