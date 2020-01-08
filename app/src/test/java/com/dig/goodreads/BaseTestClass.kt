package com.dig.goodReads

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

//import org.mockito.Mock
//import org.mockito.Mockito.mock
//import org.mockito.junit.MockitoJUnitRunner

const val TEST_ERROR = "TestError"
const val TEST_SEARCH = "TestSearch"

@RunWith(MockitoJUnitRunner::class)
open class BaseTestClass : KoinTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var application : Application

    @ExperimentalCoroutinesApi
    @Before
    open fun before(){
        Dispatchers.setMain(Dispatchers.Unconfined)
        startKoin {
            androidContext(application)
            modules(listOf(dataSourceModuleTD, viewModelModuleTD))
        }
    }

    @After
    open fun after() {
        Dispatchers.resetMain()
        stopKoin()
    }

}