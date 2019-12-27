package com.dig.goodreads.model

class Book (var id : String, var name : String) {

    override fun toString(): String {
        return "Book(id='$id', name='$name')"
    }

}