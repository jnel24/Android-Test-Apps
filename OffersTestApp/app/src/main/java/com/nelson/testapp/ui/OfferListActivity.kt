package com.nelson.testapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nelson.testapp.R
import com.nelson.testapp.data.OfferItem
import com.nelson.testapp.viewmodel.OffersViewModel
import com.nelson.testapp.viewmodel.OffersViewModel.*
import dagger.hilt.android.AndroidEntryPoint

/**
 * An activity representing a list of OrderItems.
 *
 * This activity has different presentations for handset and tablet-size devices.
 * On handsets, the activity presents a list of items, which when touched,
 * lead to a [OfferDetailActivity] representing item details.
 * On tablets, the activity presents the list of items and item
 * details (in a [OfferDetailFragment]) side-by-side using two panes.
 */
@AndroidEntryPoint
class OfferListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode (running on a tablet)
     */
    private var twoPane: Boolean = false

    private val offersViewModel: OffersViewModel by viewModels()

    private var offers : List<OfferItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        offersViewModel.getOffers()?.observe(this, {
            offers = it
            setupRecyclerView()
        })

        if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
            // If this view is present, then the activity should be in two-pane mode.
            twoPane = true
        }
    }

    override fun onResume() {
        super.onResume()

        setupRecyclerView()

        findViewById<CoordinatorLayout>(R.id.coordinator_layout).visibility = View.VISIBLE
    }

    /**
     * Sets up RecyclerView adapter and layoutManager
     *
     * @param recyclerView - The RecyclerView for setting the adapter
     */
    private fun setupRecyclerView() {
        findViewById<RecyclerView>(R.id.item_list)?.let { recyclerView ->
            // Setup Scroll to Top FAB
            val scrollUp = findViewById<FloatingActionButton>(R.id.scroll_up_fab)
            scrollUp.setOnClickListener {
                recyclerView.smoothScrollToPosition(0)
            }

            recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, offers, twoPane)
            if (!twoPane) recyclerView.layoutManager = GridLayoutManager(this, 2)
        }
    }

    /**
     * Inner Adapter class for defining RecyclerView layout behavior
     */
    class SimpleItemRecyclerViewAdapter(private val parentActivity: OfferListActivity,
                                        private val values: List<OfferItem>,
                                        private val twoPane: Boolean) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val sufficientScroll = 20

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as OfferItem
                if (twoPane) {
                    updateDetailPane(item)
                } else {
                    val intent = Intent(v.context, OfferDetailActivity::class.java).apply {
                        putExtra(OfferDetailFragment.ARG_ITEM_ID, item)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        /**
         * Updates OfferDetailFragment with the newly selected OfferItem
         *
         * @param item - The new OfferItem that was selected
         */
        private fun updateDetailPane(item: OfferItem) {
            val fragment = OfferDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(OfferDetailFragment.ARG_ITEM_ID, item)
                }
            }
            parentActivity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, fragment)
                .commit()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.order_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.orderValue.text = item.value
            holder.orderName.text = item.name
            if (item.url != null) holder.orderImage.load(item.url) {
                placeholder(R.drawable.placeholder)
            }

            holder.orderFavorite.load(if (item.isFavorite) R.drawable.favorite else R.drawable.unfavorite)

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }

            // Only show scrollUp FAB if the user has scrolled down to the 20th position
            val scrollUp = parentActivity.findViewById<FloatingActionButton>(R.id.scroll_up_fab)
            if (position > sufficientScroll) {
                scrollUp.visibility = View.VISIBLE
            } else {
                scrollUp.visibility = View.INVISIBLE
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val orderImage: ImageView = view.findViewById(R.id.order_image)
            val orderValue: TextView = view.findViewById(R.id.order_value)
            val orderName: TextView = view.findViewById(R.id.order_name)
            val orderFavorite: ImageView = view.findViewById(R.id.order_favorite)
        }
    }
}