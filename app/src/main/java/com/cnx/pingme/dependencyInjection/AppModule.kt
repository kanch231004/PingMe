package com.cnx.pingme.dependencyInjection

import android.app.Application
import androidx.work.WorkManager
import com.cnx.pingme.api.OfflineChatService
import com.cnx.pingme.room.AppDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {


    @Singleton
    @Provides
    fun provideOfflineChatService(@ChatApi okhttpClient: OkHttpClient,
                           converterFactory: GsonConverterFactory
    ) = providesOfflineService(okhttpClient,converterFactory, OfflineChatService::class.java)



    @ChatApi
    @Provides
    fun providePrivateOkHttpClient(
        upstreamClient: OkHttpClient,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return upstreamClient.newBuilder() .build()
    }

    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideChatDao(db: AppDatabase) = db.getChatDao()


    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)


    private fun createRetrofit(
            okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(OfflineChatService.ENDPOINT)

                .client(okhttpClient)
                .addConverterFactory(converterFactory)
                .build()
    }


    @Singleton
    fun  <T> providesOfflineService(okhttpClient: OkHttpClient,converterFactory: GsonConverterFactory,clazz: Class<T>) : T {

        return createRetrofit(okhttpClient,converterFactory).create(clazz)
    }

    @Provides
    fun provideWorkManager(app : Application) = WorkManager.getInstance(app)



}
