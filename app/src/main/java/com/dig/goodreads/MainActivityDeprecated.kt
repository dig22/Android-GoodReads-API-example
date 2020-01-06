package com.dig.goodreads

import android.os.Bundle
import android.os.Handler
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dig.goodreads.model.Book
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent

@Deprecated("")
class MainActivityDeprecated() : AppCompatActivity() , KoinComponent {

    val TAG = "MainActivity"

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
            if(searchView == null || searchView?.query.isNullOrBlank() )
                return "test"
            else{
                return searchView?.query.toString()
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)











    }


    override fun onResume() {
        super.onResume()
        searchView?.clearFocus()
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.main_menu, menu) //your file name
//        searchView = menu?.findItem(R.id.appBarSearch)?.actionView as SearchView
//
//        searchView?.onClose {
//            Log.d(TAG,"Search Closed")
//            bookViewModel.fetchBooks("test",page,true)
//        }
//
//        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                page = 1
//
//
//
//                return false
//            }
//
//        })
//
//        return super.onCreateOptionsMenu(menu)
//    }



    override fun onBackPressed() {
        super.onBackPressed()
//        if (searchView?.isFocused!!) {
//            searchView?.clearFocus()
//        } else {
//            super.onBackPressed()
//        }

    }
}
