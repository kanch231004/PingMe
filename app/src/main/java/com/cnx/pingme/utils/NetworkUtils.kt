package com.cnx.pingme.utils

import android.content.Context
import android.net.ConnectivityManager


fun isConnected(context : Context): Boolean {

    val connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val networkInfo = connectivityManager?.activeNetworkInfo
    return networkInfo?.isConnected ?: false
}