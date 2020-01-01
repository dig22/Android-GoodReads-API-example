package com.dig.goodreads


import android.app.Application
import com.dig.goodreads.api.BookProvider
import com.dig.goodreads.api.book.BookDetailEndPoint
import com.dig.goodreads.api.book.BookSearchEndPoint
import com.dig.goodreads.model.Book
import com.nhaarman.mockitokotlin2.capture
import net.bytebuddy.matcher.ElementMatchers.any
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


typealias MyCallback = (book: ArrayList<Book>) -> Unit

@RunWith(MockitoJUnitRunner::class)
class BookProviderUnitTest {

    lateinit var bookSearchEndPoint : BookSearchEndPoint
    @Mock lateinit var bookDetailEndPoint : BookDetailEndPoint
    @Mock lateinit var bookSearchCallback : BookSearchEndPoint.Callback
    @Mock lateinit var application: Application

    @Captor lateinit var booksCaptor: ArgumentCaptor<ArrayList<Book>>

    var bookProvider : BookProvider? = null

    @Before
    fun setUp() {
        bookSearchEndPoint = BookSearchEndPointTD()
        bookProvider = BookProvider.init(application,bookSearchEndPoint,bookDetailEndPoint)
        //bookProvider = BookProvider.INSTANCE

        //   `when`(bookSearchCallback.onFetchSuccess(any())).thenReturn()
    }


    @Test
    fun searchBooks_success_callbackWithCorrectData() {
        bookProvider?.searchBooks("test",1,bookSearchCallback)

        verify(this.bookSearchCallback).onFetchSuccess(capture(booksCaptor))

        //println(booksCaptor.value)

        Assert.assertThat(booksCaptor.value , `is`(BookSearchEndPointTD.generateBooks()))
    }

    class BookSearchEndPointTD : BookSearchEndPoint(){
        override fun searchBooks(searchString: String, page: Int, callback: Callback) {
            //super.searchBooks(null)
            callback.onFetchSuccess(generateBooks())
        }

        companion object{
            fun generateBooks(): ArrayList<Book> {
                val books = ArrayList<Book>()
                books.add(Book(1,"test1","imageURL",1))
                return books
            }
        }
    }

}