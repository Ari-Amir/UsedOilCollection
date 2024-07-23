package com.aco.usedoilcollection.uiautomator

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.platform.app.InstrumentationRegistry
import com.aco.usedoilcollection.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class DeleteLocationsTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val device: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Test
    fun test_DeleteAllLocations() {
        disableAnimations()

        device.findObject(UiSelector().text("Locations")).click()
        sleep(500)

        while (true) {
            try {
                val menuButton = device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/menu_button"))
                if (menuButton.exists()) {
                    menuButton.click()
                    device.findObject(UiSelector().text("Delete")).click()
                    device.findObject(UiSelector().text("YES")).click()
                } else {
                    break
                }
            } catch (e: Exception) {
                break
            }
        }

        val recyclerView = device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/locations_recycler_view"))
        sleep(500)
        assert(recyclerView.childCount == 0)
    }
}
