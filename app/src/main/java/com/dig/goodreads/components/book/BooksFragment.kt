package com.dig.goodreads.components.book

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dig.goodreads.R
import com.dig.goodreads.components.ui.BookListItem
import com.dig.goodreads.model.Book
import kotlinx.android.synthetic.main.fragment_books.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent


class BooksFragment : Fragment() ,OnBookClickListener, KoinComponent{

    val TAG = "MainActivity"

    val bookViewModel : BookViewModel by viewModel()

    var page = 1

    lateinit var manager : LinearLayoutManager
    var searchThrottle = Handler()

    val books : ArrayList<Book> = ArrayList()
    val bookViews : ArrayList<BookListItem> = ArrayList()

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

    lateinit var  recyclerBookList : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




//        recyclerBookList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                currentItems = manager.childCount
//                totalItems = manager.itemCount
//                scrolledOut = manager.findFirstVisibleItemPosition()
//
//                if(isScrolling && ((currentItems!! + scrolledOut!!) >= totalItems!! -1 )){
//                    //TODO progressBar.visibility = View.VISIBLE
//                    bookViewModel.fetchBooks(searchQuery,++page,false)
//                }
//            }
//
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if(newState ==  AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
//                    isScrolling = true
//                }
//            }
//        })



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val thisFragmentLayout = inflater.inflate(R.layout.fragment_books, container, false)

        manager = LinearLayoutManager(context)

        recyclerBookList = thisFragmentLayout.recyclerBookList

        recyclerBookList.layoutManager = manager

        val adapter = BookAdapter(books, this)
        recyclerBookList.adapter = adapter


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
                    this.books.add(Book(0,"PLACEHOLDER","PLACEHOLDER",1))

//                    for (book in books){
//                        this.bookViews.add(BookListItem(context,book))
//                    }

                    recyclerBookList.adapter?.notifyDataSetChanged()
                    //TODO progressBar.visibility = View.GONE
                }
            }
        })


        return thisFragmentLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
            title = getString(R.string.books)
            show()
        }

    }

    override fun bookClicked(book: Book) {
        findNavController().navigate(BooksFragmentDirections.actionBooksFragmentToDetailsFragment(book))
    }

}
