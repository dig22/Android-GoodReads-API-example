package com.dig.goodReads.components.bookDetails

import com.dig.goodReads.BaseTestClass
import com.dig.goodReads.BookRepositoryImplTD
import com.dig.goodReads.TEST_ERROR
import com.dig.goodReads.data.BookRepository
import com.dig.goodReads.model.Book
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.test.get


class BookDetailsViewModelTest : BaseTestClass() {

    private lateinit var bookRepository: BookRepository
    private lateinit var bookDetailsViewModel: BookDetailsViewModel
    private lateinit var  book : Book

    @ExperimentalCoroutinesApi
    @Before
    override fun before() {
        super.before()
        bookRepository = get()
        bookDetailsViewModel = get()

        book =
            Book(1, "testName", "", 0, "testAuthor")
    }

    @After
    override fun after(){
        super.after()
    }

    @Test
    fun bookDetails_fetchDetails_is_successful()=
        runBlocking {
            assert(bookDetailsViewModel.bookDetailsLiveData.value == BookDetailsState.Startup)
            bookDetailsViewModel.fetchDetails(book).join()
            val value = bookDetailsViewModel.bookDetailsLiveData.value

            assert(value != null)
            when (value) {
                is BookDetailsState.BookDetailsLoaded -> {
                    println("Tested Details Loaded")
                    assert(value.details.isNotBlank())
                }
                else ->{
                    assert(false)
                }
            }
        }

    @Test
    fun bookDetails_fetchDetails_is_Error() =
        runBlocking {
            setError()
            assert(bookDetailsViewModel.bookDetailsLiveData.value == BookDetailsState.Startup)
            bookDetailsViewModel.fetchDetails(book).join()
            val value = bookDetailsViewModel.bookDetailsLiveData.value

            assert(value != null)
            when (value) {
                is BookDetailsState.Error -> {
                    println("Tested Error")
                    assert(value.message == TEST_ERROR)
                }
                else ->{
                    assert(false)
                }
            }
        }

    private fun setError(){
        (bookRepository as BookRepositoryImplTD).isError = true
    }

    @Test
    fun bookDetails_fetchDetails_cachesBook()=
        runBlocking {
            assert(bookDetailsViewModel.bookDetailsLiveData.value == BookDetailsState.Startup)

            bookDetailsViewModel.fetchDetails(book).join()
            bookDetailsViewModel.fetchDetails(book).join()

            val value = bookDetailsViewModel.bookDetailsLiveData.value

            assert(value != null)
            when (value) {
                is BookDetailsState.BookDetailsLoadedFromCache -> {
                    println("Tested Details Loaded From Cache")
                    assert(value.details.isNotBlank())
                }
                else ->{
                    assert(false)
                }
            }
        }

    @Test
    fun bookDetails_fetchDetails_Invalidates_cachedBook()=
        runBlocking {
            assert(bookDetailsViewModel.bookDetailsLiveData.value == BookDetailsState.Startup)

            bookDetailsViewModel.fetchDetails(book).join()
            val newBook = Book(2, "", "", 2, "")
            bookDetailsViewModel.fetchDetails(newBook).join()

            val value = bookDetailsViewModel.bookDetailsLiveData.value

            assert(value != null)
            when (value) {
                is BookDetailsState.BookDetailsLoaded -> {
                    println("Tested Details Cache Invalidated")
                    assert(value.details.isNotBlank())
                }
                else ->{
                    assert(false)
                }
            }
        }

}