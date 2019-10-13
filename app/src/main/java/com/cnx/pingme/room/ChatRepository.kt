package com.cnx.pingme.room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.work.*
import com.cnx.pingme.api.BaseDataSource
import com.cnx.pingme.api.MessageModel
import com.cnx.pingme.utils.MSG_KEY
import com.cnx.pingme.worker.SendMsgWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(private val chatDao: ChatDao,
                                         private val workManager: WorkManager) : BaseDataSource() {


     fun sendAndReceiveChat(sentMsgModel: MessageModel, coroutineScopeIO: CoroutineScope){


        coroutineScopeIO.launch {

            insertChat(sentMsgModel)

            addRequestInQueue(sentMsgModel)
        }

    }


    fun addRequestInQueue(messageModel: MessageModel) {

        Log.d("Offline","add in request")
        createWorkRequest(messageModel)
    }


    fun insertChat(messageModel: MessageModel?) {

            messageModel?.let {
                chatDao.insertChat(messageModel)
            }
    }


    val config = PagedList.Config.Builder()
        .setPageSize(50)
        .build()


    fun getChatList (userSessionLd : LiveData<String>) = Transformations.switchMap(userSessionLd){

            session ->
        LivePagedListBuilder(chatDao.getMessageForId(session),config).build()
    }

    fun createWorkRequest(messageModel: MessageModel) {


        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()


        val stringArray : Array<String> = arrayOf<String>(messageModel.chatBotName!!, messageModel.userSession,messageModel.message!!)

        val externalId = workDataOf(
            MSG_KEY to stringArray)

        Log.d("Worker Sent","externalID ${messageModel.chatBotName} message ${messageModel.message} userSession ${messageModel.message}")

        val sendMsgRequest = OneTimeWorkRequestBuilder<SendMsgWorker>()
            .setConstraints(constraints).

                setInputData(externalId).build()


        workManager.enqueue(sendMsgRequest)

    }


}