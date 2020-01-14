package com.dig.goodReads

import com.dig.goodReads.data.BooksDataSourceFactory
import com.dig.goodReads.data.BookRepository
import com.dig.goodReads.data.BookRepositoryImpl
import com.dig.goodReads.components.books.BooksViewModel
import com.dig.goodReads.components.bookDetails.BookDetailsFetchUseCase
import com.dig.goodReads.components.bookDetails.BookDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataSourceModule = module {

    single { BookRepositoryImpl() as BookRepository }
    single { BookDetailsFetchUseCase(get()) }
    single { BooksDataSourceFactory(get()) }
}

val viewModelModule = module {
    viewModel { BookDetailsViewModel(get())}
    viewModel { BooksViewModel(get()) }
}

const val GOOD_READS_HOME = "https://www.goodreads.com/"
const val BOOK_DETAILS_API = "${GOOD_READS_HOME}book/isbn/"
const val SEARCH_API = "${GOOD_READS_HOME}search/index.xml?key=${BuildConfig.GOOD_READS_KEY}"