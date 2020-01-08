package com.dig.goodReads.model

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
            ) : Parcelable