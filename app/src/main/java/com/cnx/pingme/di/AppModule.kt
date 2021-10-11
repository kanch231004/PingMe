package com.cnx.pingme.di

import android.app.Application
import androidx.work.WorkManager
import com.cnx.pingme.api.OfflineChatService
import com.cnx.pingme.api.RetrofitFactory
import com.cnx.pingme.room.AppDatabase
import com.cnx.pingme.worker.SendMsgWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun providesStarWarsService(): OfflineChatService = RetrofitFactory.getService()

    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)


    @Singleton
    @Provides
    fun provideChatDao(db: AppDatabase) = db.getChatDao()

    @Singleton
    @Provides
    fun providesWorkManager(app: Application) = WorkManager.getInstance(app)
}
