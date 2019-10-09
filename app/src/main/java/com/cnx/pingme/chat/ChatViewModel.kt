package com.cnx.pingme.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cnx.pingme.api.ApiResponse
import com.cnx.pingme.api.ChatResponse
import com.cnx.pingme.api.RetrofitFactory

class ChatViewModel : ViewModel() {

    private val chatService = RetrofitFactory.getRetrofitClient()


    fun sendAndReceiveChat(name : String, message : String) : LiveData<ApiResponse<ChatResponse>> {

      return  chatService.getChats(name ,message)
    }

}