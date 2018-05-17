package com.aidanvii.mybingelist.core

import android.app.Activity
import android.content.Intent
import com.aidanvii.toolbox.delegates.weak.weak

class Navigator {

    var sourceActivity: Activity? by weak(null)

    fun <T : Activity> navigateTo(activityClass: Class<T>) {
        sourceActivity?.startActivity(Intent(sourceActivity, activityClass))
    }
}