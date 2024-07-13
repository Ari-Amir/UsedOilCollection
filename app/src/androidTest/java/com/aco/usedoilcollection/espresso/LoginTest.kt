package com.aco.usedoilcollection.espresso

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aco.usedoilcollection.MainActivity
import com.aco.usedoilcollection.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_Login() {
        disableAnimations()
        onView(withId(R.id.email_field)).perform(typeText("test@example.com"))
        onView(withId(R.id.password_field)).perform(typeText("password"))
        onView(withId(R.id.auth_button)).perform(click())
    }
}
