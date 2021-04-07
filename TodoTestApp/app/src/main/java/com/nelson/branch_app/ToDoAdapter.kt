package com.nelson.branch_app


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.nelson.branch_app.data.ToDoModel
import com.nelson.branch_app.utility.DialogUtilities
import kotlinx.coroutines.launch
import java.util.*

/**
 * Adapter class for defining the To-do RecyclerView's layout behavior (i.e. search filtering)
 */
class ToDoAdapter(private var todos: List<ToDoModel>,
                  private val frag: ToDoFragment) : RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    private var filtered : Vector<ToDoModel> = Vector(todos)

    val context: Context = frag.requireContext()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = if (viewType == 0) R.layout.todo_header else R.layout.todo_item
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filtered[position]
        holder.title.text =  item.title

        when (holder.itemViewType) {
            0 -> {
                holder.addToDo?.setOnClickListener {
                    DialogUtilities.showCreateToDoDialog(frag, item)
                }
            }
            else -> {
                holder.checkBox?.setOnClickListener(null)
                holder.checkBox?.isChecked = item.isChecked
                holder.checkBox?.setOnCheckedChangeListener { _, isChecked ->
                    item.isChecked = isChecked
                    frag.lifecycleScope.launch {
                        frag.todoListViewModel.addToDo(item)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = filtered[position]
        return item.type
    }

    fun setToDos(todos: List<ToDoModel>) {
        this.todos = todos
        this.filtered = Vector(todos)
        notifyDataSetChanged()
    }

    fun getToDoAt(position: Int) : ToDoModel {
        return filtered[position]
    }

    /**
     * Deletes list of to-dos or to-do item from list
     */
    fun deleteToDo(item: ToDoModel) {
        frag.lifecycleScope.launch {
            when (item.type) {
                0 -> {
                    val dialog = DialogUtilities.showDeleteListDialog(frag, item)
                    dialog.setOnDismissListener {
                        notifyDataSetChanged()
                    }
                }
                else -> frag.todoListViewModel.deleteToDo(item)
            }
        }
    }

    /**
     * "Filters" values by checking if any matches the query
     */
    fun filter(filter: String) {
        filtered.clear()
        if (filter.trim().isEmpty()) {
            filtered = Vector(todos)
        } else {
            todos.forEach {
                if (it.isMatch(filter.toLowerCase())) filtered.add(it)
            }
        }
        notifyDataSetChanged()
    }

    /**
     * Extension function for checking if ToDoModel matches the query
     */
    private fun ToDoModel.isMatch(text: String) : Boolean {
        return this.title.toLowerCase().contains(text)
    }

    override fun getItemCount() = filtered.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.todo_title)
        val delete: ImageView = view.findViewById(R.id.todo_delete)
        val addToDo: ImageView? = view.findViewById(R.id.todo_add)
        val checkBox: CheckBox? = view.findViewById(R.id.checkbox)
    }
}