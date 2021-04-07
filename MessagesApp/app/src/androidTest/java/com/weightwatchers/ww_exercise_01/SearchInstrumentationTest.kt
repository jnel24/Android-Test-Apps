package com.weightwatchers.ww_exercise_01


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SearchInstrumentationTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun searchInstrumentationTest() {
        val appCompatEditText = onView(
                allOf(withId(R.id.messages_search),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText.perform(replaceText("Quick"), closeSoftKeyboard())

        val viewGroup = onView(
                allOf(withParent(withParent(withId(R.id.messages_recycler))),
                        isDisplayed()))
        viewGroup.check(matches(isDisplayed()))

        val appCompatEditText2 = onView(
                allOf(withId(R.id.messages_search), withText("Quick"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText2.perform(click())

        val appCompatEditText3 = onView(
                allOf(withId(R.id.messages_search), withText("Quick"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText3.perform(replaceText(""))

        val appCompatEditText4 = onView(
                allOf(withId(R.id.messages_search),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText4.perform(closeSoftKeyboard())

        val appCompatEditText5 = onView(
                allOf(withId(R.id.messages_search),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText5.perform(pressImeActionButton())


        val appCompatEditText6 = onView(
                allOf(withId(R.id.messages_search),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText6.perform(replaceText("Summer"), closeSoftKeyboard())

        val recyclerView = onView(
                allOf(withId(R.id.messages_recycler),
                        childAtPosition(
                                withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                3)))
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val recyclerView2 = onView(
                allOf(withId(R.id.messages_recycler),
                        childAtPosition(
                                withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                3)))
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(1, click()))


        val textView = onView(
                allOf(withId(R.id.message_title), withText("Seasonal"),
                        withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                        isDisplayed()))
        textView.check(matches(withText("Seasonal")))

        val appCompatEditText7 = onView(
                allOf(withId(R.id.messages_search), withText("Summer"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText7.perform(pressImeActionButton())
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
