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
import kotlinx.android.synthetic.main.fragment_books.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent


class BooksFragment : Fragment() , BookPagedListAdapter.OnBookClickListener, KoinComponent{

    val TAG = "BooksFragment"

    val bookViewModel : BookViewModel by viewModel()

    lateinit var manager : LinearLayoutManager
    var searchThrottle = Handler()

    var books : PagedList<Book>?  = null



    var searchView  : SearchView? = null

    var searchQuery : String =  "Game"
        get() {
            if(searchView == null || searchView?.query.isNullOrBlank() )
                return "Game"
            else{
                return searchView?.query.toString()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val thisFragmentLayout = inflater.inflate(R.layout.fragment_books, container, false)
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

        val adapter = BookPagedListAdapter(this)

        manager = object : LinearLayoutManager(context) {
            override fun onLayoutCompleted(state: RecyclerView.State?) {
                super.onLayoutCompleted(state)
                if(adapter.itemCount > 0)
                isLoaded()
            }
        }
        recyclerBookList.layoutManager = manager



        recyclerBookList.itemAnimator = DefaultItemAnimator()
        recyclerBookList.adapter = adapter

        bookViewModel.publicState.observe(this, Observer<BooksState>{postState ->
            if (postState == null) {
                return@Observer
            }

            when(postState){
                is BooksState.Startup ->{
                    isLoading()
                    bookViewModel.search("Game")
                }
                is BooksState.Error ->{
                    isError()
                }

                is BooksState.Loading ->{
                    isLoading()
                }

                is BooksState.BooksLoadedFromCache ->{
                    isLoaded()
                }
            }

        })

        bookViewModel.getMoviesPagedList()?.observe(this, Observer<PagedList<Book>> {
            this.books = it
            adapter.submitList(books)
            recyclerBookList.adapter?.notifyDataSetChanged()
            //isLoaded()
        })

        bookFragmentReloadButton.onClick {
            bookViewModel.search(searchQuery)
        }

    }

    private fun isError(){
        bookFragmentErrorView.visibility = View.VISIBLE
        bookFragmentProgressBar.visibility = View.GONE
        recyclerBookList.visibility = View.INVISIBLE
    }

    private fun isLoading(){
        bookFragmentProgressBar.visibility = View.VISIBLE
        bookFragmentErrorView.visibility = View.GONE
        recyclerBookList.visibility = View.INVISIBLE
    }

    private fun isLoaded(){
        bookFragmentProgressBar.visibility = View.GONE
        bookFragmentErrorView.visibility = View.GONE
        recyclerBookList.visibility = View.VISIBLE
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
                    isLoading()
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
