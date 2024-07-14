package com.aco.usedoilcollection.uiautomator

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import androidx.test.platform.app.InstrumentationRegistry
import com.aco.usedoilcollection.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class EditLocationTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val device: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Test
    fun test_EditLocation() {
        disableAnimations()

        device.findObject(UiSelector().text("Locations")).click()
        sleep(500)

        val locationName = "Location 1"
        val editedLocationName = "$locationName (Edited)"

        device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/new_location_name")).setText(locationName)
        device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/add_location_button")).click()

        val recyclerView = UiScrollable(UiSelector().resourceId("com.aco.usedoilcollection:id/locations_recycler_view"))
        recyclerView.scrollIntoView(UiSelector().resourceId("com.aco.usedoilcollection:id/menu_button"))

        device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/menu_button")).click()
        device.findObject(UiSelector().text("Edit")).click()
        device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/location_name_edit")).setText(editedLocationName)
        device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/save_button")).click()

        recyclerView.scrollIntoView(UiSelector().text(editedLocationName))

        assert(device.findObject(UiSelector().text(editedLocationName)).exists())
    }
}
