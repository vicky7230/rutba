package com.vicky7230.rutba

import android.app.Application
import timber.log.Timber

class Rutba : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(object : Timber.DebugTree() {
            /*override fun log(
                priority: Int, tag: String?, message: String, t: Throwable?
            ) {
                super.log(priority, "Rutba_v${BuildConfig.VERSION_NAME}", message, t)
            }*/

            override fun createStackElementTag(element: StackTraceElement): String {
                return "Rutba_v${BuildConfig.VERSION_NAME}(${element.fileName}:${element.lineNumber})#${element.methodName}()"
            }
        })
    }
}