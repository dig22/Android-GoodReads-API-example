package com.dig.goodreads

import com.dig.goodreads.components.book.BooksDataSourceFactory
import com.dig.goodreads.api.BookRepository
import com.dig.goodreads.api.BookRepositoryImpl
import com.dig.goodreads.components.book.BooksViewModel
import com.dig.goodreads.components.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataSourceModule = module {

    single { BookRepositoryImpl() as BookRepository }
    single { BooksDataSourceFactory(get()) }
}

val viewmodelModule = module {
    viewModel { DetailsViewModel(get())}
    viewModel { BooksViewModel(get()) }
}
