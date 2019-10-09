package com.cnx.pingme

import android.app.Activity
import android.app.Application


class PingMeApp  : Application() {

    companion object {

        var appInstance: PingMeApp? = null
    }


    override fun onCreate() {
        super.onCreate()

        appInstance = this

    }

}