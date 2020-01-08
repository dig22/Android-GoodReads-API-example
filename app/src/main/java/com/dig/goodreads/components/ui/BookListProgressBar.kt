package com.dig.goodReads.components.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.dig.goodReads.R

class BookListProgressBar(context: Context) : ConstraintLayout(context){

    init {
        LayoutInflater.from(context).inflate(R.layout.view_book_list_progress_bar, this, true)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
    }
}