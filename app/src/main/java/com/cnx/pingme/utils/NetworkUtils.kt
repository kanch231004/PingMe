package com.cnx.pingme.utils

import android.content.Context
import android.net.ConnectivityManager
import com.cnx.pingme.PingMeApp.Companion.appInstance


fun isConnected(): Boolean {

    val connectivityManager = appInstance?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val networkInfo = connectivityManager?.activeNetworkInfo
    return networkInfo?.isConnected ?: false
}