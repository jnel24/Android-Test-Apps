package com.nelson.testapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import coil.load
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.nelson.testapp.R
import com.nelson.testapp.data.OfferItem
import com.nelson.testapp.databinding.FragmentItemDetailBinding
import com.nelson.testapp.ui.base.BaseFragment
import com.nelson.testapp.viewmodel.OffersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * A fragment representing a single OfferItem detail screen.
 */
@AndroidEntryPoint
class OfferDetailFragment : BaseFragment<FragmentItemDetailBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentItemDetailBinding =
        FragmentItemDetailBinding::inflate

    /**
     * The OfferItem for the fragment to present.
     */
    private var item: OfferItem? = null

    private val offersViewModel: OffersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readArguments()
        initUi()
    }

    private fun readArguments() {
        // Grab OfferItem by identifier and setup collapsing toolbar
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID) && it.getSerializable(ARG_ITEM_ID) is OfferItem) {
                item = it.getSerializable(ARG_ITEM_ID) as OfferItem
            }
        }
    }

    private fun initUi() {
        reloadFab()

        getParentActivity()?.binding?.toolbarLayout?.title = item?.value
        getParentActivity()?.binding?.detailImage?.load(item?.url)

        getParentActivity()?.binding?.fab?.setOnClickListener { view ->
            item?.let {
                it.isFavorite = !it.isFavorite
                offersViewModel.setAction(OffersViewModel.Action.UpdateOffer(it))
                reloadFab()
                val text = if (item!!.isFavorite) "favorited" else "unfavorited"
                Snackbar.make(view, "Offer $text", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }

        // Fill TextViews with data from OfferItem
        with(binding) {
            item?.let {
                itemDetailTitle.text = it.name
                itemDetailDesc.text = it.description
            }
        }
    }

    /**
     * Refreshes the FloatingActionButton's image depending on if item is a favorite or not
     */
    private fun reloadFab() {
        if (item != null) {
            getParentActivity()?.binding?.fab?.let { fab ->
                fab.load(if (item!!.isFavorite) R.drawable.favorite else R.drawable.unfavorite)
            }
        }
    }

    private fun getParentActivity() : OfferDetailActivity? {
        return activity as? OfferDetailActivity?
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}