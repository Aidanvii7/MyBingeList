package com.aidanvii.mybingelist

import android.app.Application
import com.aidanvii.mybingelist.core.logger.AndroidLoggerDelegate
import com.aidanvii.mybingelist.core.logger.CompositeLogger
import com.aidanvii.mybingelist.di.AppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        _appComponent = AppComponent(this).apply {
            dataBindingComponent.makeDefaultComponent()
        }
        CompositeLogger.attachDelegate(AndroidLoggerDelegate())
        appComponent.appSideEffects.init()
    }

    companion object {
        private lateinit var _appComponent: AppComponent
        val appComponent get() = _appComponent
    }
}