package com.nelson.testapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.nelson.testapp.models.OfferRepository

/**
 * An activity representing a single OfferItem detail screen.
 *
 * This activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [OfferListActivity].
 */
class OfferDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(findViewById(R.id.detail_toolbar))

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = OfferDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(OfferDetailFragment.ARG_ITEM_ID,
                            intent.getStringExtra(OfferDetailFragment.ARG_ITEM_ID))
                }
            }

            supportFragmentManager.beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit()
        }
    }

    override fun onPause() {
        super.onPause()
        OfferRepository.cacheOffers(this)
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