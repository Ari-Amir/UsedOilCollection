package com.aco.usedoilcollection.espresso

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aco.usedoilcollection.MainActivity
import com.aco.usedoilcollection.R
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class EditLocationTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_EditLocation() {
        disableAnimations()
        onView(withText("Locations")).perform(click())
        sleep(500)

        val locationName = "Location 1"
        val editedLocationName = "$locationName (Edited)"

        onView(withId(R.id.new_location_name)).perform(typeText(locationName))
        onView(withId(R.id.add_location_button)).perform(click())

        onView(withId(R.id.locations_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, clickChildViewWithId(R.id.menu_button)
                )
            )

        onView(withText("Edit")).perform(click())
        onView(withId(R.id.location_name_edit)).perform(click(), replaceText(editedLocationName))
        onView(withId(R.id.save_button)).perform(click())

        onView(withId(R.id.locations_recycler_view))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                   hasDescendant(withText(editedLocationName))
                )
            )
        onView(allOf(withText(editedLocationName), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).check(matches(isDisplayed()))
    }
}
