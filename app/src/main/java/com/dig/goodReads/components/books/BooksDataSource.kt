package com.dig.goodReads.components.books

import androidx.paging.PageKeyedDataSource
import com.dig.goodReads.api.BookRepository
import com.dig.goodReads.api.model.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

class BooksDataSource (private val bookRepository: BookRepository,
                       private var searchQuery : String,
                       private var errorListener : ErrorListener?,
                       private val scope: CoroutineScope = CoroutineScope(newSingleThreadContext("BookDataSource"))
) :  PageKeyedDataSource<Int, Book>() {

   // var searchQuery : String = "test"

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Book>
    ) {

        scope.launch{
            bookRepository.searchBooksNew(searchQuery,1).apply {
                when(this){
                    is BooksState.BooksLoaded ->{
                        callback.onResult(books,null,2)
                    }
                    is BooksState.Error ->{
                        errorListener?.onError(this.message!!)
                    }
                }
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {

        scope.launch{
            bookRepository.searchBooksNew(searchQuery,1).apply {
                when(this){
                    is BooksState.BooksLoaded ->{
                        callback.onResult(books, params.key+1)
                    }
                    is BooksState.Error ->{
                        errorListener?.onError(this.message!!)
                    }
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {
       //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    interface ErrorListener{
        fun onError(error : String)
    }
}