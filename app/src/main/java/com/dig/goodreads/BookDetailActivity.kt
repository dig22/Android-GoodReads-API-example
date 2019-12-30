package com.dig.goodreads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dig.goodreads.api.BookProvider
import com.dig.goodreads.model.Book
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book_detail.*

class BookDetailActivity : AppCompatActivity() {

    val TAG ="BookDetailActivity"

    lateinit var book : Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        val book = intent.getParcelableExtra<Book>("book")

        this.book = book

        fetchBookDescription(book.id)

        Picasso.get().load(book.ImageUrlLarge).into(bookDetailBookImage);
        bookDetailBookTitle.setText(book.name)

    }

    fun fetchBookDescription (bookId : Int){
        BookProvider.getBookDescription(bookId){
            Log.d(TAG, it)
            runOnUiThread{
                bookDetailBookDescription.setText(it)
            }
        }
    }
}
