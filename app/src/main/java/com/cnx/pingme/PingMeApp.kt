package com.cnx.pingme

import android.app.Activity
import android.app.Application
import androidx.work.WorkManager
import com.cnx.pingme.PingMeApp.Companion.appInstance
import com.cnx.pingme.dependencyInjection.AppInjector
import com.facebook.stetho.Stetho
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class PingMeApp : Application(), HasActivityInjector {

    @Inject
    public lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var workManager: WorkManager

    companion object {
        var appInstance: PingMeApp? = null
    }

    override fun onCreate() {
        super.onCreate()

        appInstance = this
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)

        AppInjector.init(this)

    }

    override fun activityInjector() = dispatchingAndroidInjector

}
