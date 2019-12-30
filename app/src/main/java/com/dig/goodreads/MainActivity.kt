package com.dig.goodreads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AbsListView
import android.widget.Adapter
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dig.goodreads.api.BookProvider
import com.dig.goodreads.helper.BookAdapter
import com.dig.goodreads.model.Book
import com.dig.goodreads.view.BookListItem
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.linearLayout




class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    lateinit var manager : LinearLayoutManager

    var page = 1

    var isScrolling = false

    var currentItems : Int? = null
    var totalItems : Int? = null
    var scrolledOut : Int? = null

    var listOfBooks : ArrayList<Book> = ArrayList()

    var searchQuery : String = "test"
    get() {
        if(searchView.query.isBlank()){
            return "test"
        }else{
            return searchView.query.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         manager = LinearLayoutManager(this)
         recyclerBookList.layoutManager = manager

         fetchBooks(page)

        val adapter = BookAdapter(listOfBooks,this)
        recyclerBookList.adapter = adapter

        recyclerBookList.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = manager.childCount
                totalItems = manager.itemCount
                scrolledOut = manager.findFirstVisibleItemPosition()

                if(isScrolling && ((currentItems!! + scrolledOut!!) == totalItems)){
                    fetchBooks(++page,false)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState ==  AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true
                }
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                page = 1
                fetchBooks(page,true)
                return false
            }

        })
    }

    private fun fetchBooks(page : Int = 1, refresh :Boolean = false) {
        if(refresh){
            listOfBooks.clear()
            recyclerBookList.adapter?.notifyDataSetChanged()
        }
        BookProvider.searchBooks(searchQuery,page) {
            listOfBooks.addAll(it)
            runOnUiThread{
                recyclerBookList.adapter?.notifyDataSetChanged()
            }
        }
    }
}
