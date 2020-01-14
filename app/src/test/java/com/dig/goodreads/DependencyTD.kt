package com.dig.goodReads

import com.dig.goodReads.data.BookRepository
import com.dig.goodReads.components.books.BooksState
import com.dig.goodReads.components.books.BooksViewModel
import com.dig.goodReads.components.bookDetails.BookDetailsFetchUseCase
import com.dig.goodReads.components.bookDetails.BookDetailsState
import com.dig.goodReads.components.bookDetails.BookDetailsViewModel
import com.dig.goodReads.data.BooksDataSourceFactory
import com.dig.goodReads.model.Book
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataSourceModuleTD = module {

    single { BookRepositoryImplTD() as BookRepository }
    single { BookDetailsFetchUseCase(get()) }
    single { BooksDataSourceFactory(get()) }
}

val viewModelModuleTD = module {
    viewModel { BookDetailsViewModel(get()) }
    viewModel { BooksViewModel(get()) }
}


class BookRepositoryImplTD : BookRepository{

    var isError = false
    private var books : ArrayList<Book> = ArrayList(listOf(
        Book(1, "TestName", "", 0, "")
    ))

    override suspend fun getBookDescription(bookId: Int): BookDetailsState {

        if(isError){
            return BookDetailsState.Error(TEST_ERROR)
        }

        return BookDetailsState.BookDetailsLoaded("TestDescription")
    }

    override suspend fun searchBooks(searchString: String, page: Int): BooksState {
        if(isError){
            return BooksState.Error(TEST_ERROR)
        }

        return BooksState.BooksLoaded(books)
    }
}