package com.nelson.testapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import com.nelson.testapp.R
import com.nelson.testapp.data.OfferItem
import com.nelson.testapp.databinding.ActivityItemListBinding
import com.nelson.testapp.ui.base.BaseActivity
import com.nelson.testapp.ui.recycler.SimpleOfferAdapter
import com.nelson.testapp.viewmodel.OffersViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * An activity representing a list of OfferItems.
 *
 * This activity has different presentations for handset and tablet-size devices.
 * On handsets, the activity presents a list of items, which when touched,
 * lead to a [OfferDetailActivity] representing item details.
 * On tablets, the activity presents the list of items and item
 * details (in a [OfferDetailFragment]) side-by-side using two panes.
 */
@AndroidEntryPoint
class OfferListActivity : BaseActivity<ActivityItemListBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityItemListBinding =
        ActivityItemListBinding::inflate

    /**
     * Whether or not the activity is in two-pane mode (running on a tablet)
     */
    private var twoPane: Boolean = false

    private val offersViewModel: OffersViewModel by viewModels()

    private var offers : List<OfferItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    private fun initUi() {
        with(binding) {
            setSupportActionBar(toolbar)
            toolbar.title = title
        }

        if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
            // If this view is present, then the activity should be in two-pane mode.
            twoPane = true
        }

        subscribeRecyclerToOffers()
    }

    override fun onResume() {
        super.onResume()

        binding.coordinatorLayout.visibility = View.VISIBLE

        setupRecyclerView()
    }

    private fun subscribeRecyclerToOffers() {
        offersViewModel.getOffers()?.observe(
            this,
            {
                offers = it
                setupRecyclerView()
            }
        )
    }

    /**
     * Sets up RecyclerView adapter and layoutManager
     */
    private fun setupRecyclerView() {
        binding.layoutList.itemList.let { recyclerView ->
            // Setup Scroll to Top FAB
            binding.scrollUpFab.setOnClickListener {
                recyclerView.smoothScrollToPosition(0)
            }

            recyclerView.adapter = SimpleOfferAdapter(this, offers, twoPane)
            if (!twoPane) recyclerView.layoutManager = GridLayoutManager(this, 2)
        }
    }
}