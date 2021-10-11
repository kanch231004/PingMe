package com.cnx.pingme.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cnx.pingme.api.MessageModel
import com.cnx.pingme.room.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository

) : ViewModel() {

    fun sendAndReceiveChat(messageModel: MessageModel) {
        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.sendAndReceiveChat(messageModel)
        }
    }

    var userSessionLd: MutableLiveData<String> = MutableLiveData()

    val chatList = chatRepository.getChatList(userSessionLd)


}
