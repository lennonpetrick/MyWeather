package com.test.myweather

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.EditText
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WeatherActivityTest {

    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(WeatherActivity::class.java)

    @Test
    fun getWeatherFromACity() {
        onView(withId(R.id.action_search)).perform(click())
        onView(isAssignableFrom(EditText::class.java))
                .perform(typeText("vienna"), pressImeActionButton())
        onView(withId(R.id.tvCity))
                .check(matches(isDisplayed()))
    }

}