package com.nelson.testapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.nelson.testapp.R
import com.nelson.testapp.data.OfferRepository
import com.nelson.testapp.databinding.ActivityItemDetailBinding
import com.nelson.testapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * An activity representing a single OfferItem detail screen.
 *
 * This activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [OfferListActivity].
 */
@AndroidEntryPoint
class OfferDetailActivity : BaseActivity<ActivityItemDetailBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityItemDetailBinding =
        ActivityItemDetailBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.detailToolbar)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = OfferDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(
                        OfferDetailFragment.ARG_ITEM_ID,
                            intent.getSerializableExtra(OfferDetailFragment.ARG_ITEM_ID))
                }
            }

            supportFragmentManager.beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    navigateUpTo(Intent(this, OfferListActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

}