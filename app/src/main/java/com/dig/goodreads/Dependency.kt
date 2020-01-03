package com.dig.goodreads

import com.dig.goodreads.viewmodel.BookViewModel
import com.dig.goodreads.api.BookProvider
import com.dig.goodreads.api.book.BookDetailEndPoint
import com.dig.goodreads.api.book.BookSearchEndPoint
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataSourceModule = module {

    val bookDetailEndPoint = BookDetailEndPoint()
    val bookSearchEndPoint = BookSearchEndPoint()

    single { BookProvider(bookSearchEndPoint,bookDetailEndPoint) }
}

val viewmodelModule = module {

    viewModel { BookViewModel(get()) }

   // viewModel { CommentsViewModel(get()) }
}