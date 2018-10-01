package com.test.myweather.shared

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.ref.WeakReference



class NetworkInterceptor(context: Context) : Interceptor {

    private val weakContext = WeakReference(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val context = weakContext.get()
        if (context != null && !isOnline(context)) {
            throw NoConnectionException(context)
        }

        return chain.proceed(chain.request().newBuilder().build())
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}