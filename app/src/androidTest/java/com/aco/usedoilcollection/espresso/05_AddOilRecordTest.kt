package com.aco.usedoilcollection.espresso

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aco.usedoilcollection.MainActivity
import com.aco.usedoilcollection.R
import org.hamcrest.Matchers.allOf
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddOilRecordTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeAllTests() {
            clearRecords()
        }
    }

    @Test
    fun test_AddOilRecord() {
        disableAnimations()

        for (i in 1..5) {
            val locationName = "Location $i"

            onView(withText("Input")).perform(click())
            onView(withId(R.id.liters_input)).perform(typeText(i.toString()))
            onView(withId(R.id.location_selector)).perform(click())
            onView(withText(locationName))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(scrollTo(), click())
            onView(withId(R.id.add_button)).perform(click())
            onView(withText("Yes")).perform(click())

            onView(withText("Statistics")).perform(click())

            onView(withId(R.id.statistics_recycler_view))
                .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    allOf(
                        hasDescendant(withText(locationName)),
                        hasDescendant(withText("$i Ltrs"))
                    )
                ))

            onView(allOf(withId(R.id.record_location), withText(locationName))).check(matches(isDisplayed()))
            onView(allOf(withId(R.id.record_liters), withText("$i Ltrs"))).check(matches(isDisplayed()))
        }
    }
}
