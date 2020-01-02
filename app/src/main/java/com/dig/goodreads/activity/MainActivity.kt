package com.dig.goodreads.activity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dig.goodreads.R
import com.dig.goodreads.api.book.BookSearchEndPoint
import com.dig.goodreads.api.BookProvider
import com.dig.goodreads.helper.BookAdapter
import com.dig.goodreads.model.Book
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClose


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    lateinit var manager : LinearLayoutManager
    var bookProvider : BookProvider? = null

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

    var searchThrottle = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bookProvider = BookProvider.instance

         manager = LinearLayoutManager(this)
         recyclerBookList.layoutManager = manager

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

                searchThrottle.removeCallbacksAndMessages(null)

                searchThrottle.postDelayed({
                    fetchBooks(page,true)
                }, 500)

                return false
            }

        })

       searchView.onClose {
           Log.d(TAG,"Search Closed")
       }

        fetchBooks(page)
    }

    override fun onResume() {
        super.onResume()
        searchView.clearFocus()
    }

    private fun fetchBooks(page : Int = 1, refresh :Boolean = false) {
        progressBar.visibility = View.VISIBLE
        if(refresh){
            listOfBooks.clear()
            recyclerBookList.adapter?.notifyDataSetChanged()
        }

        bookProvider?.searchBooks(searchQuery,page,object : BookSearchEndPoint.Callback{
            override fun onFetchSuccess(books: ArrayList<Book>) {
                listOfBooks.addAll(books)
                runOnUiThread{
                    recyclerBookList.adapter?.notifyDataSetChanged()
                    progressBar.visibility = View.GONE
                }
            }

            override fun onFetchFailed() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }


    override fun onBackPressed() {
        //super.onBackPressed()
        if (!searchView.isFocused) {
            searchView.clearFocus()
        } else {
            super.onBackPressed()
        }

    }
}
