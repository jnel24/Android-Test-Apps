package com.weightwatchers.ww_exercise_01

import com.weightwatchers.ww_exercise_01.data.MessageModel
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Local unit test for testing data models and api.
 */
class MessageUnitTest {

    @Test
    fun message_fieldAccess() {
        val image = "/images/1033/dynamic/foodandrecipes/2015/07/TANDORI_GRILLED_SHRIMP_1053_800x800.jpg"
        val filter = "[\\\\\\\"contentTags.food_cookingmethod.tags: Grilling\\\\\\\"]\""
        val messageModel = MessageModel(image, filter, "Summer Grilling")
        assertEquals(image, messageModel.image)
        assertEquals("Summer Grilling", messageModel.title)
        assertEquals(filter, messageModel.filter)
    }

}