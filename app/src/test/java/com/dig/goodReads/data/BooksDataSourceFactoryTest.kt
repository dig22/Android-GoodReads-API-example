package com.dig.goodReads.data

import com.dig.goodReads.BaseTestClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.koin.test.get

class BooksDataSourceFactoryTest : BaseTestClass(){

    private lateinit var booksDataSourceFactory: BooksDataSourceFactory

    @Before
    @ExperimentalCoroutinesApi
    override fun before(){
        super.before()
        booksDataSourceFactory = get()
    }

    @Test
    fun dataSource_creation_successful(){
        booksDataSourceFactory.create()
        assert(booksDataSourceFactory.booksDataSource != null)
    }
}
