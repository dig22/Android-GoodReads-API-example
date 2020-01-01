package com.dig.goodreads.api

import com.dig.goodreads.api.book.BookDetailEndPoint
import com.dig.goodreads.api.book.BookSearchEndPoint
import com.dig.goodreads.helper.SingletonHolder
import java.lang.RuntimeException

class BookProvider private constructor(val bookSearchEndPoint: BookSearchEndPoint,
                                       val bookDetailEndPoint: BookDetailEndPoint) {

    val TAG ="BookProvider"


    fun searchBooks(searchString : String,page : Int = 1,callback: BookSearchEndPoint.Callback){
        bookSearchEndPoint.searchBooks(searchString,page,callback)
    }

    fun getBookDescription(id : Int,callback: BookDetailEndPoint.Callback){
        bookDetailEndPoint.getDescription(id, callback)
    }

    companion object {

        @Volatile var INSTANCE: BookProvider? = null
       // get() =  if (INSTANCE != null) INSTANCE else throw RuntimeException("Dependency Not Injected")

        fun getInstance(bookSearchEndPoint: BookSearchEndPoint,
                        bookDetailEndPoint: BookDetailEndPoint): BookProvider =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: BookProvider(bookSearchEndPoint,bookDetailEndPoint).also { INSTANCE = it }
            }

    }
}