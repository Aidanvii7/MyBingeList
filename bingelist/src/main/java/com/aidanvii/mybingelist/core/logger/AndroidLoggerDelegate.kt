package com.aidanvii.mybingelist.core.logger

import android.util.Log

class AndroidLoggerDelegate : LoggerDelegate {
    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

    override fun v(tag: String, message: String) {
        Log.v(tag, message)
    }
}