package com.dig.goodreads.activity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.widget.AbsListView
import android.widget.ImageButton
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dig.goodreads.R
import com.dig.goodreads.helper.BookAdapter
import com.dig.goodreads.model.Book
import com.dig.goodreads.state.BookSearchState
import com.dig.goodreads.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClose
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent


class MainActivity() : AppCompatActivity() , KoinComponent {

    val TAG = "MainActivity"

    val bookViewModel : BookViewModel by viewModel()

    var page = 1

    lateinit var manager : LinearLayoutManager
    var searchThrottle = Handler()

    val books : ArrayList<Book> = ArrayList()

    var isScrolling = false
    var currentItems : Int? = null
    var totalItems : Int? = null
    var scrolledOut : Int? = null

     var searchView  : SearchView? = null

    var searchQuery : String =  "test"
        get() {
            if(searchView == null)
                return "test"
            else{
                return searchView?.query.toString()
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = LinearLayoutManager(this)
        recyclerBookList.layoutManager = manager

        val adapter = BookAdapter(books)
        recyclerBookList.adapter = adapter


        recyclerBookList.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = manager.childCount
                totalItems = manager.itemCount
                scrolledOut = manager.findFirstVisibleItemPosition()

                if(isScrolling && ((currentItems!! + scrolledOut!!) == totalItems)){
                    //TODO progressBar.visibility = View.VISIBLE
                    bookViewModel.fetchBooks(searchQuery,++page,false)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState ==  AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true
                }
            }
        })


        bookViewModel.postLiveData.observe(this, Observer<BookSearchState> { postState ->

            if (postState == null) {
                return@Observer
            }

            when (postState) {
                is BookSearchState.Startup -> {
                    bookViewModel.fetchBooks(searchQuery, page)
                }

                is BookSearchState.Loading -> {
                   //TODO progressBar.visibility = View.VISIBLE
                }

                is BookSearchState.BooksLoaded -> {
                    this.books.clear()
                    this.books.addAll(postState.books)
                    recyclerBookList.adapter?.notifyDataSetChanged()
                    //TODO progressBar.visibility = View.GONE
                }
            }
        })



    }


    override fun onResume() {
        super.onResume()
        searchView?.clearFocus()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu) //your file name
        searchView = menu?.findItem(R.id.appBarSearch)?.actionView as SearchView

        searchView?.onClose {
            Log.d(TAG,"Search Closed")
        }

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                page = 1

                searchThrottle.removeCallbacksAndMessages(null)

                searchThrottle.postDelayed({
                    //TODO   progressBar.visibility = View.VISIBLE
                    bookViewModel.fetchBooks(newText.toString(),page,true)
                }, 500)

                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }



    override fun onBackPressed() {
        super.onBackPressed()
//        if (searchView?.isFocused!!) {
//            searchView?.clearFocus()
//        } else {
//            super.onBackPressed()
//        }

    }
}
