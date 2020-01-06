package com.dig.goodreads.helper

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NewAdapter<T>(val views : List<View>) : RecyclerView.Adapter<ViewHolder2>() {

    override fun getItemCount(): Int {
        return views.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        return ViewHolder2(View(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        holder.view = views[position]
    }
}


class ViewHolder2 : RecyclerView.ViewHolder {
    // Holds the TextView that will add each animal to
    // val bookItemView = (BookListItem) book
    var view : View
    constructor(view : View) : super(view){
        this.view = view
    }
}