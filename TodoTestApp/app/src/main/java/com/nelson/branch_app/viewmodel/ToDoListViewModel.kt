package com.nelson.branch_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nelson.branch_app.data.ToDoModel
import com.nelson.branch_app.data.ToDoRepository

/**
 * ViewModel for storing the latest to-do lists
 */
class ToDoListViewModel(private val toDoRepo: ToDoRepository) : ViewModel() {

    private val toDos = toDoRepo.loadAllTodos()

    fun getToDos(): LiveData<List<ToDoModel>>? {
        return toDos
    }

    suspend fun deleteToDoList(listId: String) {
        toDoRepo.deleteTodoList(listId)
    }

    suspend fun deleteToDo(toDo: ToDoModel) {
        toDoRepo.deleteTodo(toDo)
    }

    suspend fun addToDo(toDo: ToDoModel) {
        toDoRepo.insertTodo(toDo)
    }
}