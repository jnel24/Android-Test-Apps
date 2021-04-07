package com.nelson.branch_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Model for representing To-Do Item in data
 *
 * @property id (item unique identifier)
 * @property listId (unique identifier for each list)
 * @property title (the text that is shown)
 * @property isChecked (whether the To-do is checked)
 * @property type (0 - Header & 1 - To-do Item)
 */
@Entity(tableName = "toDos")
data class ToDoModel(
        @PrimaryKey (autoGenerate = true) val id: Int = 0,
        val listId: String,
        val title: String,
        var isChecked: Boolean = false,
        val type: Int = 1)