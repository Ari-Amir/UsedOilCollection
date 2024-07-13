package com.aco.usedoilcollection.espresso

import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.runner.JUnitCore
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    RegistrationTest::class,
    AddLocationTest::class,
    AddOilRecordTest::class,
    DeleteLocationsTest::class,
    EditLocationTest::class,
    LogoutTest::class,
    LoginTest::class
)
class AllTests {

    companion object {
        private var startTime: Long = 0

        @BeforeClass
        @JvmStatic
        fun beforeAllTests() {
            startTime = System.currentTimeMillis()
            println("Testing started at: $startTime")
        }

        @AfterClass
        @JvmStatic
        fun afterAllTests() {
            val endTime = System.currentTimeMillis()
            println("Testing ended at: $endTime")
            val duration = endTime - startTime
            println("Total time taken: ${duration / 1000} seconds (${duration} milliseconds)")
        }
    }
}


class RepeatTests {

    @org.junit.Test
    fun runTestsMultipleTimes() {
        val testClasses = arrayOf<Class<*>>(
            RegistrationTest::class.java,
            AddLocationTest::class.java,
            AddOilRecordTest::class.java,
            DeleteLocationsTest::class.java,
            EditLocationTest::class.java,
            LogoutTest::class.java,
            LoginTest::class.java
        )

        for (i in 1..3) {
            println("Running tests iteration $i")
            testClasses.forEach { JUnitCore.runClasses(it) }
        }
    }
}