package com.dig.goodreads

import android.app.Application
import com.dig.goodreads.api.book.BookSearchEndPoint
import com.dig.goodreads.api.BookProvider
import com.dig.goodreads.api.book.BookDetailEndPoint

class App : Application() {

    val bookSearchEndPoint = BookSearchEndPoint()
    val bookDetailEndPoint = BookDetailEndPoint()

    override fun onCreate() {
        super.onCreate()
        BookProvider.init(this,bookSearchEndPoint,bookDetailEndPoint)
    }
}