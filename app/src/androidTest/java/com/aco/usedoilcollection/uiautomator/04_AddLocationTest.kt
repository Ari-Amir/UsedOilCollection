package com.aco.usedoilcollection.uiautomator

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.platform.app.InstrumentationRegistry
import com.aco.usedoilcollection.MainActivity
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
        private lateinit var device: UiDevice

        @BeforeClass
        @JvmStatic
        fun beforeAllTests() {
            clearLocations()
            device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        }
    }

    @Test
    fun test_AddLocation() {
        disableAnimations()

        device.findObject(UiSelector().text("Locations")).click()
        sleep(500)

        for (i in 1..5) {
            val locationName = "Location $i"
            device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/new_location_name")).setText(locationName)
            device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/add_location_button")).click()

            assert(device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/locations_recycler_view")
                .childSelector(UiSelector().text(locationName))).exists())
        }
    }
}
