package com.dig.goodreads.components.ui

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.dig.goodreads.R
import com.dig.goodreads.model.Book
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_book_list_item.view.*


class BookListItem : ConstraintLayout, View.OnClickListener {

    lateinit var book : Book

    var listener : ClickDelegate? = null

    constructor(context: Context?) : super(context){
        initView()
    }


    constructor(context: Context?, book : Book) : this(context){
        initData(book)
    }

    fun initData(book : Book){
        this.book = book
        bookItemName.setText(book.name)
        bookItemAuthor.setText("by ${book.authorName}")
        bookItemAuthor.movementMethod = ScrollingMovementMethod()
        Picasso.get().load(book.imageUrl).into(bookItemImage);
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.view_book_list_item, this, true);
    }



    override fun onClick(v: View?) {
        listener?.OnBookListItemClicked(book)
    }

    interface ClickDelegate{
        fun OnBookListItemClicked(book : Book)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
    }

}