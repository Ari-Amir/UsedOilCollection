package com.aco.usedoilcollection.espresso

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
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
class DeleteLocationsTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_DeleteAllLocations() {
        disableAnimations()
        onView(withText("Locations")).perform(click())
        sleep(500)
        while (true) {
            try {
                onView(withId(R.id.locations_recycler_view))
                    .perform(
                        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                            0, clickChildViewWithId(R.id.menu_button)
                        )
                    )
                onView(withText("Delete")).perform(click())
                onView(withText("Yes")).perform(click())
            } catch (e: Exception) {
                break
            }
        }
        onView(withId(R.id.locations_recycler_view)).check(matches(hasChildCount(0)))
    }
}
