package com.cnx.pingme

import android.app.Application
import androidx.work.WorkManager
import com.cnx.pingme.di.WorkerEntryPoint
import com.facebook.stetho.Stetho
import dagger.hilt.EntryPoints
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class PingMeApp : Application(){

    companion object {
        var appInstance: PingMeApp? = null
    }

    fun component(): WorkerEntryPoint {
        // Use EntryPoints to get an instance of the AggregatorEntryPoint.
        return EntryPoints.get(this, WorkerEntryPoint::class.java)
    }

    override fun onCreate() {
        super.onCreate()

        appInstance = this
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)

    }
}
