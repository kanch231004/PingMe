package com.cnx.pingme.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.cnx.pingme.api.OfflineChatService
import com.cnx.pingme.dependencyInjection.AppInjector
import com.cnx.pingme.room.ChatDao
import com.cnx.pingme.utils.MSG_KEY
import javax.inject.Inject

class SendMsgWorker @Inject constructor(appContext : Context,   workerParameters: WorkerParameters)
    : Worker(appContext , workerParameters) {

    init {
        AppInjector.appComponent.injectIntoWorker(this)
    }

    @Inject
    lateinit var offlineChatService: OfflineChatService

    @Inject
    lateinit var chatDao: ChatDao


    override fun doWork(): Result {

        Log.d("Worker"," Executing Work")
        try {


        val externalId = inputData.getStringArray(MSG_KEY)
        /*val message = inputData.getString(MESSAGE_CONTENT) ?: ""
        val userSession = inputData.getString(USER_SESSION) ?: ""*/


            val externalIds = externalId?.get(0) ?: ""
            val userSession = externalId?.get(1) ?: ""
            val message = externalId?.get(2) ?: ""

        Log.d("Worker","externalID ${externalIds} message ${message} userSession ${userSession}")
        val response  =  offlineChatService.getChats(externalIds,message).execute()


        if (response.isSuccessful) {

            val messagemodel = response.body()?.messageModel

            Log.d("Worker","externalID ${externalId} message $message")

            messagemodel?.let {

                it.userSession = userSession
                chatDao.insertChat(it)

            }

        }


      return Result.success()

    } catch (e : Exception) {

            e.printStackTrace()

        }

        return Result.failure()
    }


}