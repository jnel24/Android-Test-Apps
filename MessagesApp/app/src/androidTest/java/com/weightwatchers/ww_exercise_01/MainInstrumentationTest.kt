package com.weightwatchers.ww_exercise_01


import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainInstrumentationTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainInstrumentationTest() {
        val recyclerView = onView(
                allOf(withId(R.id.messages_recycler),
                        childAtPosition(
                                withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                3)))
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(2, click()))

        SystemClock.sleep(800)

        val recyclerView2 = onView(
                allOf(withId(R.id.messages_recycler),
                        childAtPosition(
                                withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                3)))
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(4, click()))

        SystemClock.sleep(800)

        val recyclerView3 = onView(
                allOf(withId(R.id.messages_recycler),
                        childAtPosition(
                                withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                3)))
        recyclerView3.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        SystemClock.sleep(800)

        val appCompatEditText = onView(
                allOf(withId(R.id.messages_search),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText.perform(replaceText("Latest"), closeSoftKeyboard())

        val viewGroup = onView(
                allOf(withParent(withParent(withId(R.id.messages_recycler))),
                        isDisplayed()))
        viewGroup.check(matches(isDisplayed()))

        val appCompatEditText2 = onView(
                allOf(withId(R.id.messages_search), withText("Latest"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText2.perform(replaceText(""))

        val appCompatEditText3 = onView(
                allOf(withId(R.id.messages_search),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText3.perform(closeSoftKeyboard())

        val appCompatEditText4 = onView(
                allOf(withId(R.id.messages_search),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText4.perform(pressImeActionButton())

        val recyclerView4 = onView(
                allOf(withId(R.id.messages_recycler),
                        childAtPosition(
                                withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                3)))
        recyclerView4.perform(actionOnItemAtPosition<ViewHolder>(2, click()))

        SystemClock.sleep(800)
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
