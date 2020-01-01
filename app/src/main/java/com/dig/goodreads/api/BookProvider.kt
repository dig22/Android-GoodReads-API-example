package com.dig.goodreads.api

import android.app.Application
import android.content.Context
import com.dig.goodreads.api.book.BookDetailEndPoint
import com.dig.goodreads.api.book.BookSearchEndPoint
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

        @Volatile var instance: BookProvider? = null

        fun init(context: Context ,bookSearchEndPoint: BookSearchEndPoint,
                        bookDetailEndPoint: BookDetailEndPoint): BookProvider {
            synchronized(this) {
                if(context !is Application){
                       throw RuntimeException("\n\nBookProvider can only be initialized with Application context\n" +
                              "if already initialized use INSTANCE directly\n\n")
                }
                if(instance == null)
                    instance =  BookProvider(bookSearchEndPoint,bookDetailEndPoint)
                return instance!!
            }
        }

    }
}