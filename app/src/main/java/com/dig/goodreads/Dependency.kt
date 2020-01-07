package com.dig.goodreads

import com.dig.goodreads.api.BookDataSource
import com.dig.goodreads.api.BookDataSourceFactory
import com.dig.goodreads.api.BookRepository
import com.dig.goodreads.api.BookRepositoryImpl
import com.dig.goodreads.api.book.BookDetailEndPoint
import com.dig.goodreads.api.book.BookSearchEndPoint
import com.dig.goodreads.components.book.BookViewModel
import com.dig.goodreads.components.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataSourceModule = module {

    val bookDetailEndPoint = BookDetailEndPoint()
    val bookSearchEndPoint = BookSearchEndPoint()

    single { BookRepositoryImpl(bookSearchEndPoint,bookDetailEndPoint) as BookRepository }
    single { BookDataSourceFactory(get()) }
}

val viewmodelModule = module {
    viewModel { DetailsViewModel(get())}
    viewModel { BookViewModel(get()) }
}
