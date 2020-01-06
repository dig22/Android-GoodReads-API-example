package com.dig.goodreads.components.book

import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dig.goodreads.components.ui.BookListItem
import com.dig.goodreads.model.Book

class BookPagedListAdapter(val bookClickListener: OnBookClickListener) : PagedListAdapter<Book,BookPagedListAdapter.BookViewHolder>(BookDiffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(BookListItem(parent.context))
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book =  getItem(position)
        holder.view.initData(book!!)
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


    class BookViewHolder : RecyclerView.ViewHolder{
        val view : BookListItem

        constructor(view : BookListItem) : super(view){
            this.view = view
        }

    }

    interface OnBookClickListener {
        fun bookClicked(book: Book)
    }
}