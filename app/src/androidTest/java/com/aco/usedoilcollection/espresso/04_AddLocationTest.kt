package com.aco.usedoilcollection.espresso

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aco.usedoilcollection.MainActivity
import com.aco.usedoilcollection.R
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class AddLocationTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeAllTests() {
            clearLocations()
        }
    }

    @Test
    fun test_AddLocation() {
        disableAnimations()
        onView(withText("Locations")).perform(click())
        sleep(500)
        for (i in 1..5) {
            val locationName = "Location $i"
            onView(withId(R.id.new_location_name)).perform(typeText(locationName))
            onView(withId(R.id.add_location_button)).perform(click())
            onView(withId(R.id.locations_recycler_view))
                .check(matches(hasDescendant(withText(locationName))))
        }
    }
}




