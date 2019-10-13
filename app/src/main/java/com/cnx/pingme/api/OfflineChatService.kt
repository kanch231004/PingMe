package com.cnx.pingme.api

import com.cnx.pingme.utils.CHATBOT_ID
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OfflineChatService {

    companion object {
        const val ENDPOINT = "https://www.personalityforge.com/"
    }

    @GET("api/chat/")
    fun getChats(@Query("externalID") externalId : String, @Query("message") message : String,
                         @Query("chatBotID") chatBotID : Int = CHATBOT_ID, @Query("apiKey") apiKey : String = apiKeyValue
    ) : Call<ChatResponse>
}