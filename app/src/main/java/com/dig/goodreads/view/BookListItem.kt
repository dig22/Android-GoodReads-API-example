package com.dig.goodreads.view

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.dig.goodreads.R
import com.dig.goodreads.model.Book
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_book_list_item.view.*

class BookListItem : ConstraintLayout {

    constructor(context: Context?) : super(context){
        initView()
    }


    constructor(context: Context?, book : Book) :  super(context){
        initView()
        initData(book)
    }

    fun initData(book : Book){
        bookItemName.setText(book.name)
        Picasso.get().load(book?.imageUrl).into(bookItemImage);
    }

    private fun initView() {
        val view = View.inflate(context, R.layout.view_book_list_item,null)
        addView(view)
    }

}