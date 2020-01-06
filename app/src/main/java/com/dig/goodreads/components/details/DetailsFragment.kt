package com.dig.goodreads.components.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.dig.goodreads.R
import com.dig.goodreads.model.Book
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent


class DetailsFragment : Fragment() , KoinComponent {


    lateinit var  bookDetailBookDescription : TextView
    lateinit var book : Book;

    private val args: DetailsFragmentArgs by navArgs()

    val detailsViewModel : DetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val thisFragmentLayout = inflater.inflate(R.layout.fragment_details, container, false)

        this.book = args.book

        this.bookDetailBookDescription = thisFragmentLayout.bookDetailBookDescription

        Picasso.get().load(this.book.ImageUrlLarge).into(thisFragmentLayout.bookDetailBookImage)
        thisFragmentLayout.bookDetailBookTitle.setText(book.name)

        detailsViewModel.postLiveData.observe(this, Observer<DetailsState> { postState ->

            if (postState == null) {
                return@Observer
            }

            when (postState) {
                //TODO : Other States
                is DetailsState.Startup -> {
                    detailsViewModel.fetchDetails(book)
                }
                is DetailsState.DetailsLoaded ->{
                    this.bookDetailBookDescription.setText(postState.details)
                }
            }
        })

        return thisFragmentLayout
    }

    private fun fetchAndCacheDescription(book: Book) {



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.apply {
            show()
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = getString(R.string.details)
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity!!.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
