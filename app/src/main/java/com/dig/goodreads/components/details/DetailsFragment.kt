package com.dig.goodReads.components.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.dig.goodReads.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent


class DetailsFragment : Fragment() , KoinComponent {


    private val args: DetailsFragmentArgs by navArgs()
    private val detailsViewModel : DetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    private fun detailsLoaded(details : String){
        bookDetailsProgressBar.visibility = View.GONE
        bookDetailBookDescription.text = details
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.apply {
            show()
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = getString(R.string.details)
        }

        val book = args.book

        Picasso.get().load(book.ImageUrlLarge).into(bookDetailBookImage)
        bookDetailBookTitle.text = book.name

        detailsViewModel.detailsLiveData.observe(this, Observer<DetailsState> { detailsState ->

            if (detailsState == null) {
                return@Observer
            }

            when (detailsState) {
                //TODO : Other States
                is DetailsState.Startup -> {
                    bookDetailsProgressBar.visibility = View.VISIBLE
                    detailsViewModel.fetchDetails(book)
                }
                is DetailsState.DetailsLoaded ->{
                    detailsLoaded(detailsState.details)
                }
                is DetailsState.DetailsLoadedFromCache ->{
                    detailsLoaded(detailsState.details)
                }
            }
        })
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
