package com.weightwatchers.ww_exercise_01


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.weightwatchers.ww_exercise_01.data.MessageModel
import java.util.*


private const val BASE_URL = "https://www.weightwatchers.com"

/**
 * Adapter class for defining the Messages RecyclerView's layout behavior (i.e. search filtering)
 */
class MessagesAdapter(private val activity: MainActivity, private val values: List<MessageModel>) : RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    private var filtered : Vector<MessageModel> = Vector(values)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filtered[position]
        holder.title.text =  item.title
        holder.image.load(BASE_URL + item.image) {
            placeholder(R.drawable.placeholder)
            error(R.drawable.placeholder)
        }

        if (item.filter.isNotEmpty()) holder.itemView.setOnClickListener {
            activity.displaySnackbar(item.filter)
        }
    }

    /**
     * "Filters" values by checking if any matches the query
     */
    fun filter(filter: String) {
        filtered.clear()
        if (filter.trim().isEmpty()) {
            filtered = Vector(values)
        } else {
            values.forEach {
                if (it.isMatch(filter.toLowerCase())) filtered.add(it)
            }
        }
        notifyDataSetChanged()
    }

    /**
     * Extension function for checking if MessageModel matches the query
     */
    private fun MessageModel.isMatch(text: String) : Boolean {
        return this.filter.toLowerCase().contains(text) || this.title.toLowerCase().contains(text)
    }

    override fun getItemCount() = filtered.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.message_image)
        val title: TextView = view.findViewById(R.id.message_title)
    }
}