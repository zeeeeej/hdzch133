package com.yunnext.pad.app

import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun useAppContext() {
        // Context of the app under test.

        println("light_171247".toByteArray().toHexString())
    }
}