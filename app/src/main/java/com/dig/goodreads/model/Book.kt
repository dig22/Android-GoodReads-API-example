package com.dig.goodreads.model

class Book (var id : String, var name : String, var imageUrl : String) {

    override fun toString(): String {
        return "Book(id='$id', name='$name')"
    }

}