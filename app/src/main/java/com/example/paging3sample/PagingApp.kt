package com.example.paging3sample

import android.app.Application
import timber.log.Timber

class PagingApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return "${element.fileName}:${element.lineNumber})#${element.methodName}"
                }
            })
        }
    }
}