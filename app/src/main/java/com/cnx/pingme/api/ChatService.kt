package com.cnx.pingme.api

import androidx.lifecycle.LiveData
import com.cnx.pingme.utils.CHATBOT_ID
import retrofit2.http.GET
import retrofit2.http.Query


const val apiKeyValue = "6nt5d1nJHkqbkphe"

interface ChatService {

     @GET("api/chat/")
     fun getChats(@Query("externalID") externalId : String,  @Query("message") message : String,
                  @Query("chatBotID") chatBotID : Int = CHATBOT_ID,@Query("apiKey") apiKey : String = apiKeyValue
                    ) : LiveData<ApiResponse<ChatResponse>>

}
