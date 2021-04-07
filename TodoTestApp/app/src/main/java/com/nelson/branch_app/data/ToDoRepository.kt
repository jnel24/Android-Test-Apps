package com.nelson.branch_app.data

import androidx.lifecycle.LiveData

/**
 * Repository class for AppDatabase and ToDoDao
 */
class ToDoRepository(var database: AppDatabase) {
    private var todos: LiveData<List<ToDoModel>>? = null

    fun loadAllTodos(): LiveData<List<ToDoModel>>? {
        todos = database.toDoDao()?.getToDos()
        return todos
    }

    suspend fun insertTodo(toDo: ToDoModel) {
        database.toDoDao()?.insert(toDo)
    }

    suspend fun deleteTodoList(listId: String) {
        database.toDoDao()?.deleteToDoList(listId)
    }

    suspend fun deleteTodo(toDo: ToDoModel?) {
        database.toDoDao()?.deleteToDo(toDo)
    }

}