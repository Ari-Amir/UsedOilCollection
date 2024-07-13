package com.aco.usedoilcollection.espresso


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.aco.usedoilcollection.database.AppDatabase
import com.aco.usedoilcollection.database.dao.UserDao
import kotlinx.coroutines.runBlocking


fun clearUsers() {
    disableAnimations()
    lateinit var userDao: UserDao
    lateinit var database: AppDatabase
    val context = ApplicationProvider.getApplicationContext<Context>()
    database = Room.databaseBuilder(context, AppDatabase::class.java, "oil_collection_database").build()
    userDao = database.userDao()
    runBlocking {
        userDao.deleteAllUsers()
    }
}

fun disableAnimations() {
    val instrumentation = InstrumentationRegistry.getInstrumentation()
    instrumentation.uiAutomation.executeShellCommand("settings put global window_animation_scale 0")
    instrumentation.uiAutomation.executeShellCommand("settings put global transition_animation_scale 0")
    instrumentation.uiAutomation.executeShellCommand("settings put global animator_duration_scale 0")
}
