package com.weightwatchers.ww_exercise_01


import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class DeviceRotationInstrumentationTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun deviceRotationTest() {
        Matchers.allOf(ViewMatchers.withParent(ViewMatchers.withParent(ViewMatchers.withId(R.id.messages_recycler))),
                ViewMatchers.isDisplayed())

        mActivityTestRule.activity.requestedOrientation = SCREEN_ORIENTATION_LANDSCAPE
        SystemClock.sleep(800)

        Matchers.allOf(ViewMatchers.withParent(ViewMatchers.withParent(ViewMatchers.withId(R.id.messages_recycler))),
                ViewMatchers.isDisplayed())

        mActivityTestRule.activity.requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
        SystemClock.sleep(800)

        Matchers.allOf(ViewMatchers.withParent(ViewMatchers.withParent(ViewMatchers.withId(R.id.messages_recycler))),
                ViewMatchers.isDisplayed())

        SystemClock.sleep(800)

    }
}
