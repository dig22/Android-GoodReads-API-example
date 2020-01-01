package com.dig.goodreads.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dig.goodreads.App
import com.dig.goodreads.R
import com.dig.goodreads.api.BookProvider
import com.dig.goodreads.api.book.BookDetailEndPoint
import com.dig.goodreads.model.Book
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book_detail.*

class BookDetailActivity : AppCompatActivity() {

    val TAG ="BookDetailActivity"

    var bookProvider : BookProvider? = null


    lateinit var book : Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        bookProvider = BookProvider.INSTANCE

        actionBar?.setDisplayHomeAsUpEnabled(true);

        val book = intent.getParcelableExtra<Book>("book")

        this.book = book

        fetchBookDescription(book.id)

        Picasso.get().load(book.ImageUrlLarge).into(bookDetailBookImage);
        bookDetailBookTitle.setText(book.name)

    }

    fun fetchBookDescription (bookId : Int){

        bookProvider?.getBookDescription(bookId,object : BookDetailEndPoint.Callback{
            override fun onFetchSuccess(description: String) {
                runOnUiThread{
                    bookDetailBookDescription.setText(description)
            }
            }

            override fun onFetchFailed() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }
}
