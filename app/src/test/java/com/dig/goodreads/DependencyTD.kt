package com.dig.goodReads

import com.dig.goodReads.data.BookRepository
import com.dig.goodReads.components.books.BooksState
import com.dig.goodReads.components.books.BooksViewModel
import com.dig.goodReads.components.details.DetailsState
import com.dig.goodReads.components.details.DetailsViewModel
import com.dig.goodReads.data.BooksDataSourceFactory
import com.dig.goodReads.model.Book
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataSourceModuleTD = module {

    single { BookRepositoryImplTD() as BookRepository }
    single { BooksDataSourceFactory(get()) }
}

val viewModelModuleTD = module {
    viewModel { DetailsViewModel(get()) }
    viewModel { BooksViewModel(get()) }
}


class BookRepositoryImplTD : BookRepository{

    var isError = false
    var books : ArrayList<Book> = ArrayList(listOf(
        Book(1, "TestName", "", 0, "")
    ))

    override suspend fun getBookDescription(bookId: Int): DetailsState {

        if(isError){
            return DetailsState.Error(TEST_ERROR)
        }

        return DetailsState.DetailsLoaded("TestDescription")
    }

    override suspend fun searchBooks(searchString: String, page: Int): BooksState {
        if(isError){
            return BooksState.Error(TEST_ERROR)
        }

        return BooksState.BooksLoaded(books)
    }
}