package com.nelson.branch_app

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * ItemTouchHelper Callback for implementing Swipe To Delete
 */
class SwipeToDeleteCallback(private val adapter: ToDoAdapter) :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val background: GradientDrawable = GradientDrawable(GradientDrawable.Orientation.TL_BR, intArrayOf(Color.RED, Color.RED))

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        val iconRes = if (viewHolder.itemViewType == 0) R.drawable.ic_delete_list else R.drawable.ic_delete
        val radius = if (viewHolder.itemViewType == 0) 0f else 8.toPX().toFloat()
        val icon: Drawable = ContextCompat.getDrawable(adapter.context, iconRes)!!

        val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconBottom = iconTop + icon.intrinsicHeight

        when {
            dX > 0 -> {
                // Swiping to the right
                val iconLeft = itemView.left + iconMargin + icon.intrinsicWidth
                val iconRight = itemView.left + iconMargin
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            }
            dX < 0 -> {
                // Swiping to the left
                val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                val iconRight = itemView.right - iconMargin
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                background.setBounds(itemView.right + dX.toInt(),
                        itemView.top, itemView.right, itemView.bottom)
            }
            else -> {
                // view is unSwiped
                background.setBounds(0, 0, 0, 0)
            }
        }
        background.cornerRadii = floatArrayOf(0f, 0f, radius, radius, radius, radius, 0f, 0f)
        background.draw(c)
        icon.draw(c)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.LEFT) {
            try {
                val toDo = adapter.getToDoAt(viewHolder.adapterPosition)
                adapter.deleteToDo(toDo)
            } catch (ex: IndexOutOfBoundsException) {
                Log.e("DELETE_FAILED", ex.toString())
            }
        }
    }
}