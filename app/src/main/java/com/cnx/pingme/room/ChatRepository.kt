package com.cnx.pingme.room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.cnx.pingme.api.BaseDataSource
import com.cnx.pingme.api.ChatService
import com.cnx.pingme.api.MessageModel
import com.cnx.pingme.api.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(private val chatDao: ChatDao,
                                         private val chatService: ChatService) : BaseDataSource() {



     fun sendAndReceiveChat(sentMsgModel: MessageModel, coroutineScopeIO: CoroutineScope){

         Log.d("sendMsg","${sentMsgModel.toString()}")
        coroutineScopeIO.launch {


            insertChat(sentMsgModel)
        }

        coroutineScopeIO.launch {

            val result =  getResult { chatService.getChats(sentMsgModel.userSession,sentMsgModel.message ?: "") }
            Log.d("messageModel","${result.data?.messageModel}")

            if (result.status == Result.Status.SUCCESS) {

                val receiveMsgModel = result.data?.messageModel
                receiveMsgModel?.userSession = sentMsgModel?.userSession

                Log.d("messageModel","${receiveMsgModel.toString()}")
                insertChat(receiveMsgModel)

            }

        }
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


}