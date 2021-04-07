package com.nelson.branch_app.data

import androidx.lifecycle.LiveData
import androidx.room.*


/**
 * The Data Access Object for the ToDoModel class.
 */
@Dao
interface ToDoDao {
    @Query("SELECT * FROM todos ORDER BY listId, type")
    fun getToDos(): LiveData<List<ToDoModel>>

    @Query("SELECT * FROM todos WHERE listId = :listId")
    fun getTodoList(listId: String): LiveData<List<ToDoModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: ToDoModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(todos: List<ToDoModel>)

    @Delete
    suspend fun deleteToDo(toDo: ToDoModel?)

    @Query("DELETE FROM todos WHERE listId = :listId")
    suspend fun deleteToDoList(listId: String?)
}