package com.aco.usedoilcollection.uiautomator

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import androidx.test.platform.app.InstrumentationRegistry
import com.aco.usedoilcollection.MainActivity
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddOilRecordTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    companion object {
        private lateinit var device: UiDevice

        @BeforeClass
        @JvmStatic
        fun beforeAllTests() {
            clearRecords()
            device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        }
    }

    @Test
    fun test_AddOilRecord() {
        disableAnimations()

        for (i in 1..5) {
            val locationName = "Location $i"

            device.findObject(UiSelector().text("Input")).click()
            device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/liters_input")).setText(i.toString())
            device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/location_selector")).click()
            device.findObject(UiSelector().text(locationName)).click()
            device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/add_button")).click()
            device.findObject(UiSelector().text("Yes")).click()

            device.findObject(UiSelector().text("Statistics")).click()

            val recyclerView = UiScrollable(UiSelector().resourceId("com.aco.usedoilcollection:id/statistics_recycler_view"))
            recyclerView.scrollIntoView(UiSelector().text(locationName))
            recyclerView.scrollIntoView(UiSelector().text("$i Ltrs"))

            assert(device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/record_location").text(locationName)).exists())
            assert(device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/record_liters").text("$i Ltrs")).exists())
        }
    }
}
