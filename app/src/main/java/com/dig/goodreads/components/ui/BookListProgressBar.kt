package com.dig.goodreads.components.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.dig.goodreads.R

class BookListProgressBar : ConstraintLayout{

    constructor(context: Context) :   super(context){
        LayoutInflater.from(context).inflate(R.layout.view_book_list_progress_bar, this, true);
//        layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
//        parent.lay
    }
}