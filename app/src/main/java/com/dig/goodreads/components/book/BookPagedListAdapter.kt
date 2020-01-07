package com.dig.goodreads.components.book

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dig.goodreads.components.ui.BookListItem
import com.dig.goodreads.components.ui.BookListProgressBar
import com.dig.goodreads.model.Book

class BookPagedListAdapter(val bookClickListener: OnBookClickListener) : PagedListAdapter<Book,BookPagedListAdapter.BookViewHolder>(BookDiffCallback){


    private val TYPE_BOOK_ITEM = 1
    private val TYPE_PROGRESS_BAR = 2

    override fun getItemViewType(position: Int): Int {
        if(position == getItemCount()-1){
            return TYPE_PROGRESS_BAR
        }
        return  TYPE_BOOK_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        when(viewType){
            TYPE_PROGRESS_BAR -> return BookViewHolder(
                BookListProgressBar(parent.context)
            )
            else ->  return BookViewHolder(
                BookListItem(parent.context)
            )
        }
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        if ( holder.view is BookListProgressBar) return

        val book =  getItem(position)
        (holder.view as BookListItem).initData(book!!)
        holder.view.setOnClickListener{
            bookClickListener.bookClicked(book)
        }
    }


    companion object {
            val BookDiffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
        }
    }


    class BookViewHolder(val view : View) : RecyclerView.ViewHolder(view){

    }

    interface OnBookClickListener {
        fun bookClicked(book: Book)
    }
}