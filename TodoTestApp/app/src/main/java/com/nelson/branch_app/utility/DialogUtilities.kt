package com.nelson.branch_app.utility

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.nelson.branch_app.R
import com.nelson.branch_app.ToDoFragment
import com.nelson.branch_app.data.ToDoModel
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Utilities object for displaying dialogs
 */
object DialogUtilities {

    fun showCreateToDoDialog(frag: ToDoFragment, header: ToDoModel) {
        val dialog = createDialog(frag)
        val body = dialog.findViewById(R.id.dialog_title) as TextView
        body.text = "What would you like your new to-do item for ${header.title} to be called?"
        val input = dialog.findViewById(R.id.dialog_input) as EditText
        input.hint = "Apples"
        val confirm = dialog.findViewById(R.id.dialog_confirm) as ImageView
        confirm.setOnClickListener {
            val title = input.text.toString()
            frag.lifecycleScope.launch {
                frag.todoListViewModel.addToDo(ToDoModel(0, header.listId, title))
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    fun showCreateListDialog(frag: ToDoFragment) {
        val dialog = createDialog(frag)
        val body = dialog.findViewById(R.id.dialog_title) as TextView
        body.text = "What would you like your new to-do list to be called?"
        val input = dialog.findViewById(R.id.dialog_input) as EditText
        input.hint = "Groceries, Tasks, etc."
        val confirm = dialog.findViewById(R.id.dialog_confirm) as ImageView
        confirm.setOnClickListener {
            val title = input.text.toString()
            frag.lifecycleScope.launch {
                frag.todoListViewModel.addToDo(ToDoModel(0, Random.nextInt().toString(), title, type = 0))
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    fun showDeleteListDialog(frag: ToDoFragment, header: ToDoModel) : Dialog {
        val dialog = createDialog(frag)
        val body = dialog.findViewById(R.id.dialog_title) as TextView
        body.text = "Are you sure you want to delete the entire to-do list named ${header.title}?"
        val input = dialog.findViewById(R.id.dialog_input) as EditText
        input.visibility = View.GONE
        val confirm = dialog.findViewById(R.id.dialog_confirm) as ImageView
        confirm.setOnClickListener {
            frag.lifecycleScope.launch {
                frag.todoListViewModel.deleteToDoList(header.listId)
                dialog.dismiss()
            }
        }
        dialog.show()
        return dialog
    }

    private fun createDialog(frag: ToDoFragment) : Dialog {
        val dialog = Dialog(frag.requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog)
        val cancel = dialog.findViewById(R.id.dialog_cancel) as ImageView
        cancel.setOnClickListener { dialog.dismiss() }
        return dialog
    }
}