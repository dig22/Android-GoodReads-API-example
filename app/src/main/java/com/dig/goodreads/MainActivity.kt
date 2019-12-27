package com.dig.goodreads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dig.goodreads.api.BookProvider


const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BookProvider.searchBooks("test") {
            Log.d(TAG,it.toString())
        }
    }
}
