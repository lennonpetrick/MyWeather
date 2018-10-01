package com.test.myweather.shared

import android.content.Context
import com.test.myweather.R
import java.io.IOException

class NoConnectionException(private val context: Context) : IOException() {
    override val message: String?
        get() = context.getString(R.string.no_connection_error)
}
