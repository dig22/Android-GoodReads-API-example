package com.dig.goodreads

import com.dig.goodreads.components.book.BookDataSourceFactory
import com.dig.goodreads.api.BookRepository
import com.dig.goodreads.api.BookRepositoryImpl
import com.dig.goodreads.api.book.BookSearchEndPoint
import com.dig.goodreads.components.book.BookViewModel
import com.dig.goodreads.components.details.DetailsViewModel
import kotlinx.coroutines.newSingleThreadContext
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

val dataSourceModule = module {

    single { BookSearchEndPoint()}

    single { BookRepositoryImpl(get()) as BookRepository }
    single { BookDataSourceFactory(get()) }
}

val viewmodelModule = module {
    viewModel { DetailsViewModel(get())}
    viewModel { BookViewModel(get()) }
}
