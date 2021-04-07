package com.nelson.branch_app

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.nelson.branch_app.data.AppDatabase
import com.nelson.branch_app.data.ToDoRepository
import com.nelson.branch_app.databinding.FragTodoBinding
import com.nelson.branch_app.utility.DialogUtilities
import com.nelson.branch_app.viewmodel.ToDoListViewModel

/**
 * Fragment class for displaying the To-do lists and managing search bar
 */
class ToDoFragment : Fragment() {

    lateinit var todoListViewModel : ToDoListViewModel

    private val binding : FragTodoBinding by lazy { FragTodoBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupDatabase()
        return binding.root.rootView
    }

    private fun setupDatabase() {
        val database = AppDatabase.getInstance(requireContext())
        if (database != null) {
            val toDoRepo = ToDoRepository(database)
            todoListViewModel = ToDoListViewModel(toDoRepo)
            setupRecycler()
            setupFab()
            setupSearchBar()
        } else {
            Log.e("DATABASE_ERROR", "App Database could not be created.")
        }

    }

    private fun setupRecycler() {
        binding.todoRecycler.layoutManager = LinearLayoutManager(activity)
        val adapter = ToDoAdapter(emptyList(), this)
        binding.todoRecycler.adapter = adapter
        ItemTouchHelper(SwipeToDeleteCallback(adapter)).attachToRecyclerView(binding.todoRecycler)
        todoListViewModel.getToDos()?.observe(this, androidx.lifecycle.Observer {
            binding.todoEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            adapter.setToDos(it)
        })
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            DialogUtilities.showCreateListDialog(this)
        }
    }

    private fun setupSearchBar() {
        binding.todoSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                (binding.todoRecycler.adapter as ToDoAdapter?)?.filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}