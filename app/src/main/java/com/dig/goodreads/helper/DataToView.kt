package com.dig.goodreads.helper

import android.content.Context
import android.view.View
import com.dig.goodreads.model.Book
import com.dig.goodreads.components.ui.BookListItem

object DataToView{
    public fun BookToView(context : Context, book: Book) : View {
        return BookListItem(context, book)
    }
}