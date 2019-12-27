package com.dig.goodreads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.dig.goodreads.api.BookProvider
import com.dig.goodreads.helper.BookAdapter
import com.dig.goodreads.view.BookListItem
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.linearLayout


const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        BookProvider.searchBooks("test") {
            runOnUiThread{
                val adapter = BookAdapter(it,this)
                val manager = LinearLayoutManager(this)
                recyclerBookList.adapter = adapter
                recyclerBookList.layoutManager = manager    
            }
//            for (book in it){
//                runOnUiThread{
//                    linearLayout.addView(BookListItem(this, book ))
//                }
//            }
        }
    }
}
