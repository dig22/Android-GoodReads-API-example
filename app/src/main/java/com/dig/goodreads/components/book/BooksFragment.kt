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
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dig.goodreads.R
import com.dig.goodreads.model.Book
import kotlinx.android.synthetic.main.fragment_books.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent


class BooksFragment : Fragment() , BookPagedListAdapter.OnBookClickListener, KoinComponent{

    val TAG = "BooksFragment"

    val bookViewModelWithPaging : BookViewModelWithPaging by viewModel()

    lateinit var manager : LinearLayoutManager
    var searchThrottle = Handler()

    var books : PagedList<Book>?  = null



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



        bookViewModelWithPaging.getMoviesPagedList()?.observe(this, Observer<PagedList<Book>> {
            this.books = it

            val adapter = BookPagedListAdapter(this)
            adapter.submitList(books)

            recyclerBookList.itemAnimator = DefaultItemAnimator()
            recyclerBookList.adapter = adapter
            recyclerBookList.adapter?.notifyDataSetChanged()

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
