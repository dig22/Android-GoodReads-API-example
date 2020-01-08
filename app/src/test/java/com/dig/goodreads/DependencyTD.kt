package com.dig.goodreads

import com.dig.goodreads.api.BookRepository
import com.dig.goodreads.api.BookRepositoryImpl
import com.dig.goodreads.components.book.BooksDataSourceFactory
import com.dig.goodreads.components.book.BooksState
import com.dig.goodreads.components.book.BooksViewModel
import com.dig.goodreads.components.details.DetailsState
import com.dig.goodreads.components.details.DetailsViewModel
import com.dig.goodreads.model.Book
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataSourceModuleTD = module {

    single { BookRepositoryImplTD() as BookRepository }
    single { BooksDataSourceFactory(get()) }
}

val viewmodelModuleTD = module {
    viewModel { DetailsViewModel(get()) }
    viewModel { BooksViewModel(get()) }
}


class BookRepositoryImplTD : BookRepository{

    var isError = false
    var books : ArrayList<Book> = ArrayList(listOf(
        Book(1,"TestName","",0,"")
    ))


    override suspend fun getBookDescription(bookId: Int): DetailsState {

        if(isError){
            return DetailsState.Error(TEST_ERROR)
        }

        return DetailsState.DetailsLoaded("TestDescription")
    }

    override suspend fun searchBooksNew(searchString: String, page: Int): BooksState {
        if(isError){
            return BooksState.Error(TEST_ERROR)
        }

        return BooksState.BooksLoaded(books)
    }
}