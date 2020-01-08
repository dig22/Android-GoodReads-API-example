package com.dig.goodreads

import com.dig.goodreads.api.BookRepository
import com.dig.goodreads.components.details.DetailsState
import com.dig.goodreads.components.details.DetailsViewModel
import com.dig.goodreads.model.Book
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.test.get


class DetailsViewModelTest : BaseTestClass() {

    private lateinit var bookRepository: BookRepository
    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var  book : Book

    @ExperimentalCoroutinesApi
    @Before
    override fun before() {
        super.before()
        bookRepository = get()
        detailsViewModel = get()

        book = Book(1,"testName","",0,"testAuthor")
    }

    @Test
    fun bookDetails_fetchDetails_is_successful()=
        runBlocking {
            assert(detailsViewModel.detailsLiveData.value == DetailsState.Startup)
            detailsViewModel.fetchDetails(book).join()
            val value = detailsViewModel.detailsLiveData.value

            assert(value != null)
            when (value) {
                is DetailsState.DetailsLoaded -> {
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
            assert(detailsViewModel.detailsLiveData.value == DetailsState.Startup)
            detailsViewModel.fetchDetails(book).join()
            val value = detailsViewModel.detailsLiveData.value

            assert(value != null)
            when (value) {
                is DetailsState.Error -> {
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
            assert(detailsViewModel.detailsLiveData.value == DetailsState.Startup)

            detailsViewModel.fetchDetails(book).join()
            detailsViewModel.fetchDetails(book).join()

            val value = detailsViewModel.detailsLiveData.value

            assert(value != null)
            when (value) {
                is DetailsState.DetailsLoadedFromCache -> {
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
            assert(detailsViewModel.detailsLiveData.value == DetailsState.Startup)

            detailsViewModel.fetchDetails(book).join()
            val newBook = Book(2,"","",2,"")
            detailsViewModel.fetchDetails(newBook).join()

            val value = detailsViewModel.detailsLiveData.value

            assert(value != null)
            when (value) {
                is DetailsState.DetailsLoaded -> {
                    println("Tested Details Cache Invalidated")
                    assert(value.details.isNotBlank())
                }
                else ->{
                    assert(false)
                }
            }
        }

}