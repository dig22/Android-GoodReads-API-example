package com.dig.goodReads.components.ui

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.dig.goodReads.R
import com.dig.goodReads.model.Book
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_book_list_item.view.*


class BookListItem(context: Context) : ConstraintLayout(context), View.OnClickListener {

    lateinit var book : Book

    private var listener : ClickDelegate? = null

    init {
        initView()
    }

    fun initData(book : Book){
        this.book = book
        bookItemName.text = book.name
        bookItemAuthor.text = resources.getText(R.string.byAuthor,book.authorName)
        bookItemAuthor.movementMethod = ScrollingMovementMethod()
        Picasso.get().load(book.imageUrl).into(bookItemImage)
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.view_book_list_item, this, true)
    }



    override fun onClick(v: View?) {
        listener?.onBookListItemClicked(book)
    }

    interface ClickDelegate{
        fun onBookListItemClicked(book : Book)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
    }

}