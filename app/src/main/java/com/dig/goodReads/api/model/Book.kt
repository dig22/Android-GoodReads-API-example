package com.dig.goodReads.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Book (val id : Int,
            val name : String,
            val imageUrl : String,
            val authorId : Int,
            val authorName : String,
            var ImageUrlLarge : String? = null,
            var details : String? = null
            ) : Parcelable {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}