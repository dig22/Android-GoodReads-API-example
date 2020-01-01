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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (id != other.id) return false
        if (name != other.name) return false
        if (imageUrl != other.imageUrl) return false
        if (authorId != other.authorId) return false
        if (ImageUrlLarge != other.ImageUrlLarge) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + authorId
        result = 31 * result + (ImageUrlLarge?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        return result
    }
}