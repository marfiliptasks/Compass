package com.mf.compass.uiHelpers

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.mf.compass.R


fun updateBackgroundColor(degrees: Float, context: Context, view: View) {
    if (!degrees.isNaN()) {
        val value = when {
            degrees < -360 -> degrees + 360
            degrees > 360 -> degrees - 360
            else -> degrees
        }
        view.setBackgroundColor(ContextCompat.getColor(context, getColor(value)))
    }
}

private fun getColor(degrees: Float): Int =
    when {
        degrees > -20 && degrees < 20 -> R.color.colorPositive
        degrees > -40 && degrees < 40 -> R.color.colorPositive1
        degrees > -60 && degrees < 60 -> R.color.colorPositive2
        degrees > -80 && degrees < 80 -> R.color.colorPositive3
        degrees > -100 && degrees < 100 -> R.color.colorNeutral
        degrees > -120 && degrees < 120 -> R.color.colorNeutral1
        degrees > -140 && degrees < 140 -> R.color.colorNegative3
        degrees > -160 && degrees < 160 -> R.color.colorNegative2
        degrees > -180 && degrees < 180 -> R.color.colorNegative
        degrees > -200 && degrees < 200 -> R.color.colorNegative
        degrees > -220 && degrees < 220 -> R.color.colorNegative2
        degrees > -240 && degrees < 240 -> R.color.colorNegative3
        degrees > -260 && degrees < 260 -> R.color.colorNeutral1
        degrees > -280 && degrees < 280 -> R.color.colorNeutral
        degrees > -300 && degrees < 300 -> R.color.colorPositive3
        degrees > -320 && degrees < 320 -> R.color.colorPositive2
        degrees > -340 && degrees < 340 -> R.color.colorPositive1
        degrees > -360 && degrees < 360 -> R.color.colorPositive
        else -> R.color.colorPositive
    }

