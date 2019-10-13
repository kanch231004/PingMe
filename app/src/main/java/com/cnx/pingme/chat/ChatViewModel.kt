package com.cnx.pingme.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cnx.pingme.api.MessageModel
import com.cnx.pingme.dependencyInjection.CoroutineScopeIO
import com.cnx.pingme.room.ChatRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ChatViewModel @Inject constructor(private val chatRepository: ChatRepository,
                                        @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope

): ViewModel() {


    fun sendAndReceiveChat(messageModel: MessageModel) {

        chatRepository.sendAndReceiveChat(messageModel,ioCoroutineScope)
    }

    var userSessionLd : MutableLiveData<String> = MutableLiveData()

    val chatList = chatRepository.getChatList(userSessionLd)


    fun getUserSession() = userSessionLd





}