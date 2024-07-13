package com.aco.usedoilcollection.espresso

import org.junit.runner.JUnitCore
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    RegistrationTest::class,
    LogoutTest::class,
    LoginTest::class
)
class AllTests

class RepeatTests {

    @org.junit.Test
    fun runTestsMultipleTimes() {
        val testClasses = arrayOf<Class<*>>(
            RegistrationTest::class.java,
            LogoutTest::class.java,
            LoginTest::class.java
        )

        for (i in 1..10) {
            println("Running tests iteration $i")
            testClasses.forEach { JUnitCore.runClasses(it) }
        }
    }
}