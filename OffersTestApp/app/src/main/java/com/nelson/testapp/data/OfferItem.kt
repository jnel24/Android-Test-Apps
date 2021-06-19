package com.nelson.testapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable

/**
 * A model representing an Offer.
 *
 * @property id - The identifier of the offer
 * @property url - The web link to the product's image for the offer
 * @property name - The name of the product for the offer
 * @property description - The details of the offer
 * @property value - The current "cash back" value of the offer
 * @property isFavorite - Whether the offer is a favorite of the user or not
 */

@Entity(tableName = "offers")
class OfferItem(@PrimaryKey val id: String,
                val url: String?,
                val name: String,
                val description: String,
                @Json(name = "current_value") val value: String,
                var isFavorite: Boolean = false) : Serializable