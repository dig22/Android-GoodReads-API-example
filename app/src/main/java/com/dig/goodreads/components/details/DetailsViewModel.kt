package com.dig.goodReads.components.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dig.goodReads.helper.CoroutineViewModel
import com.dig.goodReads.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

class DetailsViewModel (private val detailsFetchUseCase : DetailsFetchUseCase,
                        uiContext: CoroutineContext = Dispatchers.Main) :
    KoinComponent,  CoroutineViewModel(uiContext) {

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
        privateState.value = detailsFetchUseCase.getBookDescription(book.id).also {
            when(it){
                is DetailsState.DetailsLoaded ->{
                    book.details = it.details
                }
            }
        }
    }
}