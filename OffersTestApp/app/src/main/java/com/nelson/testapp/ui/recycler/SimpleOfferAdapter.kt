package com.nelson.testapp.ui.recycler

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nelson.testapp.R
import com.nelson.testapp.data.OfferItem
import com.nelson.testapp.databinding.ItemOfferBinding
import com.nelson.testapp.extensions.getLayoutInflater
import com.nelson.testapp.ui.OfferDetailActivity
import com.nelson.testapp.ui.OfferDetailFragment
import com.nelson.testapp.ui.OfferListActivity

/**
 * Inner Adapter class for defining RecyclerView layout behavior
 */
class SimpleOfferAdapter(
    private val parentActivity: OfferListActivity,
    private val values: List<OfferItem>,
    private val twoPane: Boolean
) : RecyclerView.Adapter<SimpleOfferAdapter.OfferViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val binding = ItemOfferBinding.inflate(parent.getLayoutInflater(), parent, false)
        return OfferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val item = values[position]
        holder.offerValue.text = item.value
        holder.offerName.text = item.name
        if (item.url != null) holder.offerImage.load(item.url) {
            placeholder(R.drawable.placeholder)
        }

        holder.offerFavorite.load(if (item.isFavorite) R.drawable.favorite else R.drawable.unfavorite)

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }

        // Only show scrollUp FAB if the user has scrolled down to the 20th position
        parentActivity.binding.scrollUpFab.isVisible = position > sufficientScroll
    }

    override fun getItemCount() = values.size

    inner class OfferViewHolder(binding: ItemOfferBinding) : RecyclerView.ViewHolder(binding.root) {
        val offerImage: ImageView = binding.offerImage
        val offerValue: TextView = binding.offerValue
        val offerName: TextView = binding.offerName
        val offerFavorite: ImageView = binding.offerFavorite
    }
}