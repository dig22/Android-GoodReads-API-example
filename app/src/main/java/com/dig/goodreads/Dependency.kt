package com.dig.goodreads

import com.dig.goodreads.activity.BookDetailActivity
import com.dig.goodreads.activity.MainActivity
import com.dig.goodreads.api.BookProvider
import com.dig.goodreads.api.book.BookDetailEndPoint
import com.dig.goodreads.api.book.BookSearchEndPoint
import org.koin.dsl.module

val dataSourceModule = module {

    single { BookProvider(BookSearchEndPoint(), BookDetailEndPoint()) }
   // single { BookDetailActivity(get()) }
   // single { MainActivity(get()) }
}