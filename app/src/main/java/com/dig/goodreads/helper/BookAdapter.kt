package com.dig.goodreads.helper

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dig.goodreads.model.Book
import com.dig.goodreads.view.BookListItem

class BookAdapter(val books : ArrayList<Book>, val context : Context) : RecyclerView.Adapter<ViewHolder>(){

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(BookListItem(context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.view as BookListItem).initData(books[position])
    }
}

class ViewHolder : RecyclerView.ViewHolder {
    // Holds the TextView that will add each animal to
   // val bookItemView = (BookListItem) book

    val view : View

    constructor(view : BookListItem) : super(view){
        this.view = view
    }
}