package com.nelson.testapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.nelson.testapp.models.OfferItem
import com.nelson.testapp.models.OfferRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * A fragment representing a single OfferItem detail screen.
 *
 * This fragment is either contained in a [OfferListActivity]
 * in two-pane mode (on tablets) or a [OfferDetailActivity]
 * on handsets.
 */
class OfferDetailFragment : Fragment(), CoroutineScope {

    /**
     * The OfferItem for the fragment to present.
     */
    private var item: OfferItem? = null

    private val fab : FloatingActionButton? by lazy { activity?.findViewById<FloatingActionButton>(R.id.fab) }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    /**
     * Refreshes the FloatingActionButton's image depending on if item is a favorite or not
     */
    private fun reloadFab() {
        if (item != null) fab?.load(if (item!!.isFavorite) R.drawable.favorite else R.drawable.unfavorite)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // Grab OfferItem by identifier and setup collapsing toolbar
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                item = OfferRepository.getItem(it.getString(ARG_ITEM_ID)!!)
                val toolBarLayout = activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)
                toolBarLayout?.title = item?.value
                val image = activity?.findViewById<ImageView>(R.id.detail_image)
                image?.load(item?.url)
            }
        }

        reloadFab()

        fab?.setOnClickListener { view ->
            item?.isFavorite = !item!!.isFavorite
            reloadFab()
            val text = if (item!!.isFavorite) "favorited" else "unfavorited"
            Snackbar.make(view, "Order $text", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // Fill TextViews with data from OfferItem
        item?.let {
            rootView.findViewById<TextView>(R.id.item_detail_title).text = it.name
            rootView.findViewById<TextView>(R.id.item_detail_desc).text = it.description
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}