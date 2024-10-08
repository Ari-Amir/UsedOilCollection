package com.aco.usedoilcollection.uiautomator


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.aco.usedoilcollection.database.AppDatabase
import com.aco.usedoilcollection.database.dao.LocationDao
import com.aco.usedoilcollection.database.dao.OilCollectionRecordDao
import com.aco.usedoilcollection.database.dao.UserDao
import kotlinx.coroutines.runBlocking

const val TEST_USER_NAME = "Test User"
const val TEST_USER_EMAIL = "test@example.com"
const val TEST_USER_PASSWORD = "password"

fun clearRecords() {
    disableAnimations()
    lateinit var oilCollectionRecordDao: OilCollectionRecordDao
    lateinit var database: AppDatabase
    val context = ApplicationProvider.getApplicationContext<Context>()
    database = Room.databaseBuilder(context, AppDatabase::class.java, "oil_collection_database").build()
    oilCollectionRecordDao = database.oilCollectionRecordDao()
    runBlocking {
        oilCollectionRecordDao.deleteAllRecords()
        oilCollectionRecordDao.resetAutoIncrementRecords()
    }
}

fun clearUsers() {
    disableAnimations()
    lateinit var userDao: UserDao
    lateinit var database: AppDatabase
    val context = ApplicationProvider.getApplicationContext<Context>()
    database = Room.databaseBuilder(context, AppDatabase::class.java, "oil_collection_database").build()
    userDao = database.userDao()
    runBlocking {
        userDao.deleteAllUsers()
        userDao.resetAutoIncrementUsers()
    }
}

fun clearLocations() {
    disableAnimations()
    lateinit var locationDao: LocationDao
    lateinit var database: AppDatabase
    val context = ApplicationProvider.getApplicationContext<Context>()
    database = Room.databaseBuilder(context, AppDatabase::class.java, "oil_collection_database").build()
    locationDao = database.locationDao()
    runBlocking {
        locationDao.deleteAllLocations()
        locationDao.resetAutoIncrementLocations()
    }
}

fun disableAnimations() {
    val instrumentation = InstrumentationRegistry.getInstrumentation()
    instrumentation.uiAutomation.executeShellCommand("settings put global window_animation_scale 0")
    instrumentation.uiAutomation.executeShellCommand("settings put global transition_animation_scale 0")
    instrumentation.uiAutomation.executeShellCommand("settings put global animator_duration_scale 0")
}
