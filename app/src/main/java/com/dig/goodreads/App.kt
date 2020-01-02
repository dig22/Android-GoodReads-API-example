package com.dig.goodreads

import android.app.Application
import com.dig.goodreads.api.book.BookSearchEndPoint
import com.dig.goodreads.api.BookProvider
import com.dig.goodreads.api.book.BookDetailEndPoint
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(dataSourceModule))
        }
    }
}