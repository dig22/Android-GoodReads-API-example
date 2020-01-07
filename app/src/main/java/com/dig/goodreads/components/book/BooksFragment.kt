package com.dig.goodreads.components.book

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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

    val bookViewModel : BookViewModel by viewModel()

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
        setHasOptionsMenu(true);

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



        bookViewModel.getMoviesPagedList()?.observe(this, Observer<PagedList<Book>> {
            this.books = it

            val adapter = BookPagedListAdapter(this)
            adapter.submitList(books)

            recyclerBookList.itemAnimator = DefaultItemAnimator()
            recyclerBookList.adapter = adapter
            recyclerBookList.adapter?.notifyDataSetChanged()

        })


        bookViewModel.search("test")

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

    override fun onPrepareOptionsMenu(menu: Menu) {
        val mSearchMenuItem: MenuItem = menu.findItem(R.id.appBarSearch)
        searchView = mSearchMenuItem.getActionView() as SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                bookViewModel.search(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchThrottle.removeCallbacksAndMessages(null)

                searchThrottle.postDelayed({
                    //TODO   progressBar.visibility = View.VISIBLE
                    bookViewModel.search(searchQuery)

                }, 500)

                return false
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu ,   inflater : MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu)
    }

}
