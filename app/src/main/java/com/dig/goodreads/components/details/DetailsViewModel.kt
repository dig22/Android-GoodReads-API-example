package com.dig.goodreads.components.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dig.goodreads.api.BookRepository
import com.dig.goodreads.helper.CouroutineViewModel
import com.dig.goodreads.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

class DetailsViewModel (private val bookRepository : BookRepository,
                        uiContext: CoroutineContext = Dispatchers.Main) :
    KoinComponent,  CouroutineViewModel(uiContext) {

    private val privateState = MutableLiveData<DetailsState>()


    val detailsLiveData : LiveData<DetailsState>
        get() = privateState

    init {
        privateState.value = DetailsState.Startup
    }


    fun fetchDetails(book : Book) = launch {
        if(book.details != null){
            privateState.value = DetailsState.DetailsLoadedFromCache(book.details!!)
            return@launch
        }
        privateState.value = DetailsState.Loading
        privateState.value = bookRepository.getBookDescription(book.id).also {
            when(it){
                is DetailsState.DetailsLoaded ->{
                    book.details = it.details
                }
            }
        }
    }
}