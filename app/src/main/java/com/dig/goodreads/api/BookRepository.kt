package com.dig.goodreads.api

import com.dig.goodreads.api.book.BookDetailEndPoint
import com.dig.goodreads.api.book.BookSearchEndPoint

class BookRepositoryImpl constructor(val bookSearchEndPoint: BookSearchEndPoint,
                                     val bookDetailEndPoint: BookDetailEndPoint)
    :BookRepository{

    val TAG ="BookProvider"


    override fun searchBooks(searchString : String,page : Int,callback: BookSearchEndPoint.Callback?){
        bookSearchEndPoint.searchBooks(searchString,page, callback )
    }

    override fun getBookDescription(id : Int,callback: BookDetailEndPoint.Callback?){
        bookDetailEndPoint.getDescription(id, callback)
    }

}

interface BookRepository {
     fun searchBooks(searchString : String,page : Int = 1,callback: BookSearchEndPoint.Callback?)
     fun getBookDescription(bookId: Int,callback: BookDetailEndPoint.Callback?)
}