package com.dig.goodreads.components.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dig.goodreads.api.BookRepository
import com.dig.goodreads.api.book.BookDetailEndPoint
import com.dig.goodreads.components.book.BookSearchState
import com.dig.goodreads.model.Book
import org.koin.core.KoinComponent

class DetailsViewModel (private val bookRepository : BookRepository) :  ViewModel() , KoinComponent {

    private val privateState = MutableLiveData<DetailsState>()


    val postLiveData : LiveData<DetailsState>
        get() = privateState

    init {
        privateState.value = DetailsState.Startup
    }

    fun fetchDetails(book : Book) {
        if(book.description != null){
            privateState.value = DetailsState.DetailsLoaded(book.description!!)
            return
        }
            bookRepository.getBookDescription(book.id,object : BookDetailEndPoint.Callback{
                override fun onFetchSuccess(description: String) {
                    book.description = description
                    privateState.postValue(DetailsState.DetailsLoaded(description))
                }

                override fun onFetchFailed() {
                    privateState.postValue(DetailsState.Error("Error"))
                }

            })

    }
}