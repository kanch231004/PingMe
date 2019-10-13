package com.cnx.pingme.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.cnx.pingme.api.MessageModel
import com.cnx.pingme.api.OfflineChatService
import com.cnx.pingme.dependencyInjection.AppInjector
import com.cnx.pingme.room.ChatDao
import com.cnx.pingme.utils.CHATBOT_ID
import com.cnx.pingme.utils.MSG_KEY
import java.util.*
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


        val msg = inputData.getStringArray(MSG_KEY)

            val externalId = msg?.get(0) ?: ""
            val userSession = msg?.get(1) ?: ""
            val message = msg?.get(2) ?: ""
            val msgId = msg?.get(3)!!

        Log.d("Worker","externalID ${externalId} message ${message} userSession ${userSession} msgId ${msgId}")
        val response  =  offlineChatService.getChats(externalId,message).execute()


      Log.d("Response ","${response}")


        if (response.isSuccessful) {


            if (response.body()?.success == 1) {

            val messagemodel = response.body()?.messageModel

            messagemodel?.let {

                it.userSession = userSession
                it.id = UUID.randomUUID().toString()
                chatDao.insertChat(it)
                messagemodel.isSuccess = true
            }
        }

            else {

                Log.d("Update","update msgID ${msgId}")
                val messageModel = MessageModel(msgId,userSession, CHATBOT_ID,externalId,null,message,true,false)
                chatDao.insertChat(messageModel)
            }

        } else {

            val messageModel = MessageModel(msgId,userSession, CHATBOT_ID,externalId,null,message,true,false)
            chatDao.insertChat(messageModel)
        }

      return Result.success()

    }  catch (e : Exception) {

            e.printStackTrace()

    }

        return Result.failure()
    }



}