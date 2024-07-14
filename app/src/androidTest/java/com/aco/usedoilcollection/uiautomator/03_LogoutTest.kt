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
class LogoutTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val device: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Test
    fun test_Logout() {
        disableAnimations()

        device.findObject(UiSelector().text("Account")).click()
        sleep(500)
        device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/logout_button")).click()

        assert(device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/email_field")).exists())
        assert(device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/password_field")).exists())
        assert(device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/toggle_button")).exists())
        assert(device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/auth_button")).exists())
    }
}
