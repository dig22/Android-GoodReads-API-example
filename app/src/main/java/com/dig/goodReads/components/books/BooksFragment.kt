package com.dig.goodReads.components.books

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
import com.dig.goodReads.R
import com.dig.goodReads.model.Book
import kotlinx.android.synthetic.main.fragment_books.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent



class BooksFragment : Fragment() , BooksPagedListAdapter.OnBookClickListener, KoinComponent{

    private val booksViewModel : BooksViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
            title = getString(R.string.books)
            show()
        }

        val adapter = BooksPagedListAdapter(this)

        val manager = object : LinearLayoutManager(context) {
            override fun onLayoutCompleted(state: RecyclerView.State?) {
                super.onLayoutCompleted(state)
                if(adapter.itemCount > 0)
                isLoaded()
            }
        }
        recyclerBookList.layoutManager = manager



        recyclerBookList.itemAnimator = DefaultItemAnimator()
        recyclerBookList.adapter = adapter

        booksViewModel.booksLiveData.observe(this, Observer<BooksState>{ postState ->
            if (postState == null) {
                return@Observer
            }

            when(postState){
                is BooksState.Startup ->{
                    isLoading()
                    booksViewModel.search("Game")
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

        var books : PagedList<Book>

        booksViewModel.getMoviesPagedList()?.observe(this, Observer<PagedList<Book>> {
            books = it
            adapter.submitList(books)
            recyclerBookList.adapter?.notifyDataSetChanged()
            //isLoaded()
        })
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
        val searchView = mSearchMenuItem.actionView as SearchView

        bookFragmentReloadButton.onClick {
            if(searchView.query.isNullOrBlank())
                booksViewModel.search(resources.getString(R.string.defaultSearchString),true)
            else{
                booksViewModel.search(searchView.query.toString(),true)
            }
        }

        val searchThrottle = Handler()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                booksViewModel.search(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchThrottle.removeCallbacksAndMessages(null)

                searchThrottle.postDelayed({
                    isLoading()

                    if( searchView.query.isNullOrBlank()){
                        booksViewModel.search(resources.getString(R.string.defaultSearchString))
                    }else{
                        booksViewModel.search(newText!!)
                    }

                }, 500)

                return false
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu ,   inflater : MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

}
