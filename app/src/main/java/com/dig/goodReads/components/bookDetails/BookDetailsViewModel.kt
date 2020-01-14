package com.dig.goodReads.components.bookDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dig.goodReads.helper.CoroutineViewModel
import com.dig.goodReads.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

class BookDetailsViewModel (private val bookDetailsFetchUseCase : BookDetailsFetchUseCase,
                            uiContext: CoroutineContext = Dispatchers.Main) :
    KoinComponent,  CoroutineViewModel(uiContext) {

    private val privateState = MutableLiveData<BookDetailsState>()


    val bookDetailsLiveData : LiveData<BookDetailsState>
        get() = privateState

    init {
        privateState.value = BookDetailsState.Startup
    }


    fun fetchDetails(book : Book) = launch {
        if(book.details != null){
            privateState.value = BookDetailsState.BookDetailsLoadedFromCache(book.details!!)
            return@launch
        }
        privateState.value = BookDetailsState.Loading
        privateState.value = bookDetailsFetchUseCase.getBookDescription(book.id).also {
            when(it){
                is BookDetailsState.BookDetailsLoaded ->{
                    book.details = it.details
                }
            }
        }
    }
}