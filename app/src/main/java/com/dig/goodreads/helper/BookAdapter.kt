package com.dig.goodreads.helper

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dig.goodreads.model.Book
import com.dig.goodreads.view.BookListItem
import com.dig.goodreads.view.BookListProgressBar

class BookAdapter(val books : List<Book>) : RecyclerView.Adapter<ViewHolder>(){

    private val TYPE_BOOK_ITEM = 1
    private val TYPE_PROGRESS_BAR = 2

    override fun getItemCount(): Int {
        return books.size
    }

    override fun getItemViewType(position: Int): Int {
        if(position == getItemCount()-1){
            return TYPE_PROGRESS_BAR
        }
        return  TYPE_BOOK_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when(viewType){
            TYPE_PROGRESS_BAR -> return ViewHolder(BookListProgressBar(parent.context))
            else ->  return ViewHolder(BookListItem(parent.context))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder.view is BookListProgressBar) return
        (holder.view as BookListItem).initData(books[position])
    }
}

class ViewHolder : RecyclerView.ViewHolder {
    // Holds the TextView that will add each animal to
   // val bookItemView = (BookListItem) book

    val view : View

    constructor(view : View) : super(view){
        this.view = view
        //this.
//        val lp: ViewGroup.LayoutParams = view.getLayoutParams()
        //lp.width = view.;

    }
}