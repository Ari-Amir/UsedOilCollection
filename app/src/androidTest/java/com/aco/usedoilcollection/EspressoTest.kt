package com.aco.usedoilcollection

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.ActivityTestRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aco.usedoilcollection.database.AppDatabase
import com.aco.usedoilcollection.database.dao.UserDao
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class EspressoTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var userDao: UserDao
    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.databaseBuilder(context, AppDatabase::class.java, "oil_collection_database").build()
        userDao = database.userDao()
    }

    @Test
    fun test1_Registration() {
        runBlocking {
            userDao.deleteAllUsers()
        }
        onView(withId(R.id.toggle_button)).perform(click())
        onView(withId(R.id.name_field)).perform(typeText("Test User"))
        onView(withId(R.id.email_field)).perform(typeText("test@example.com"))
        onView(withId(R.id.password_field)).perform(typeText("password"))
        onView(withId(R.id.auth_button)).perform(click())
    }

    @Test
    fun test2_Logout() {
        onView(withText("Account")).perform(click())
        onView(withId(R.id.logout_button)).perform(click())
    }

    @Test
    fun test3_Login() {
        onView(withId(R.id.email_field)).perform(typeText("test@example.com"))
        onView(withId(R.id.password_field)).perform(typeText("password"))
        onView(withId(R.id.auth_button)).perform(click())
    }
}
