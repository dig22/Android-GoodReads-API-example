package com.dig.goodReads

import com.dig.goodReads.data.BooksDataSourceFactory
import com.dig.goodReads.data.BookRepository
import com.dig.goodReads.data.BookRepositoryImpl
import com.dig.goodReads.components.books.BooksViewModel
import com.dig.goodReads.components.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataSourceModule = module {

    single { BookRepositoryImpl() as BookRepository }
    single { BooksDataSourceFactory(get()) }
}

val viewModelModule = module {
    viewModel { DetailsViewModel(get())}
    viewModel { BooksViewModel(get()) }
}
