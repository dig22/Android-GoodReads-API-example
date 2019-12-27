package com.dig.goodreads

import android.os.Build
import android.util.Log
import com.dig.goodreads.api.BookProvider
import com.dig.goodreads.model.Book
import io.mockk.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


typealias MyCallback = (book: ArrayList<Book>) -> Unit

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class BookProviderUnitTest {

    @Test
    fun can_fetchBooks() {

        val mock = spyk<BookProvider>()
        val cb = mockk<MyCallback>(relaxed = true)
        mock.searchBooks("test",cb)

        verify (timeout = 5000){ cb.invoke(any()) }

    }
}