package com.nelson.branch_app

import com.nelson.branch_app.data.ToDoModel
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Local unit test for testing data models.
 */
class ToDoUnitTest {

    @Test
    fun todo_fieldAccess() {
        val todoModel = ToDoModel(0, 0.toString(), "Title")
        assertEquals("Title", todoModel.title)
        assertEquals(0, todoModel.id)
        assertEquals("0", todoModel.listId)
        assertEquals(false, todoModel.isChecked)
        assertEquals(1, todoModel.type)
    }

}