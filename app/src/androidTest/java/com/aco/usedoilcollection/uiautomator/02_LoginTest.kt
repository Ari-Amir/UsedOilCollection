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

@RunWith(AndroidJUnit4::class)
class LoginTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val device: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Test
    fun test_Login() {
        device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/email_field")).setText(TEST_USER_EMAIL)
        device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/password_field")).setText(TEST_USER_PASSWORD)
        device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/auth_button")).click()

        device.findObject(UiSelector().text("Account")).click()
        val username = device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/username")).text
        val email = device.findObject(UiSelector().resourceId("com.aco.usedoilcollection:id/email")).text

        assert(username == TEST_USER_NAME)
        assert(email == TEST_USER_EMAIL)
    }
}
