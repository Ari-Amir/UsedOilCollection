package com.aco.usedoilcollection.espresso

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aco.usedoilcollection.MainActivity
import com.aco.usedoilcollection.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class LogoutTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_Logout() {
        disableAnimations()
        onView(withText("Account")).perform(click())
        sleep(500)
        onView(withId(R.id.logout_button)).perform(click())

        onView(withId(R.id.email_field)).check(matches(isDisplayed()))
        onView(withId(R.id.password_field)).check(matches(isDisplayed()))
        onView(withId(R.id.toggle_button)).check(matches(isDisplayed()))
        onView(withId(R.id.auth_button)).check(matches(isDisplayed()))
    }
}
