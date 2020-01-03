package com.dig.goodreads.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dig.goodreads.R
import com.dig.goodreads.api.BookProvider
import com.dig.goodreads.api.book.BookDetailEndPoint
import com.dig.goodreads.model.Book
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book_detail.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class BookDetailActivity : AppCompatActivity() , KoinComponent {

    val TAG ="BookDetailActivity"

    val bookProvider : BookProvider by inject()


    lateinit var book : Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        //bookProvider = BookProvider()

        actionBar?.setDisplayHomeAsUpEnabled(true);

        val book = intent.getParcelableExtra<Book>("book")

        this.book = book

        fetchBookDescription(book.id)

        Picasso.get().load(book.ImageUrlLarge).into(bookDetailBookImage);
        bookDetailBookTitle.setText(book.name)

    }

    fun fetchBookDescription (bookId : Int){

        bookDetailsProgressBar.visibility = View.VISIBLE

        bookProvider?.getBookDescription(bookId,object : BookDetailEndPoint.Callback{
            override fun onFetchSuccess(description: String) {
                runOnUiThread{
                    bookDetailBookDescription.setText(description)
                    bookDetailsProgressBar.visibility = View.GONE
                 }
            }

            override fun onFetchFailed() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }


}
