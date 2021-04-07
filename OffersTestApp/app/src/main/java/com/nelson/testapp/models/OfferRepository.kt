package com.nelson.testapp.models

import android.app.Activity
import android.content.Context
import android.util.Log
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.ArrayList

/**
 * A static object for storing and accessing the OfferItems.
 */
object OfferRepository {

    /**
     * An ArrayList of OfferItem objects for filling the list.
     */
    val ITEMS: ArrayList<OfferItem> = ArrayList()

    private const val cacheName = "OFFERS"

    /**
     * Sets the OfferRepository's ITEMS with the provided ArrayList.
     *
     * @param items - The new items for the OfferRepository
     */
    @JvmStatic
    fun setItems(items: List<OfferItem>) {
        ITEMS.clear()
        ITEMS.addAll(items)
    }

    /**
     * Retrieves the OfferItem corresponding to the given id
     *
     * @param id - The id of the OfferItem to be returned
     *
     * @return the requested OfferItem or null if it doesn't exist
     */
    fun getItem(id: String) : OfferItem? {
        ITEMS.forEach {
            if (it.id == id) return it
        }
        return null
    }

    /**
     * Caches the offers to save user's favorite offers.
     *
     * @param activity - Activity needed to write to file
     */
    fun cacheOffers(activity: Activity): Boolean {
        Thread().run {
            try {
                activity.openFileOutput(cacheName, Context.MODE_PRIVATE).use { fos ->
                    val oos = ObjectOutputStream(fos)
                    oos.writeObject(ITEMS)
                    oos.close()
                }
            } catch (e: Exception) {
                Log.d("CACHE", "Caching error for $cacheName")
            }
            return true
        }
    }

    /**
     * Loads the offers to recover user's favorite offers.
     *
     * @param activity - Activity needed to read from file
     */
    fun loadOffers(activity: Activity): Any? {
        Thread().run {
            try {
                activity.openFileInput(cacheName).use { fis ->
                    val ois = ObjectInputStream(fis)
                    val data = ois.readObject()
                    ois.close()
                    return data
                }
            } catch (e: Exception) {
                Log.d("CACHE", "Couldn't read cache for $cacheName")
                return null
            }
        }
    }
}