package com.dig.goodreads.components.details

import androidx.lifecycle.ViewModel
import com.dig.goodreads.api.BookRepository
import com.dig.goodreads.model.Book
import org.koin.core.KoinComponent

class DetailsViewModel (private val bookRepository : BookRepository) :  ViewModel() , KoinComponent {


    fun fetchDetails(book : Book) {

//        bookRepository.(searchQuery,page,object : BookSearchEndPoint.Callback{
//
//        })
    }
}