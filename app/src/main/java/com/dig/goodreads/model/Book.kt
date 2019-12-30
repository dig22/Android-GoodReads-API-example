package com.dig.goodreads.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Book (var id : Int,
            var name : String,
            var imageUrl : String,
            var authorId : Int,
            var ImageUrlLarge : String? = null,
            var description : String? = null
            ) : Parcelable {

    override fun toString(): String {
        return "Book(id='$id', name='$name')"
    }

}