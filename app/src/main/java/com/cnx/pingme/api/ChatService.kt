package com.cnx.pingme.api

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Query


const val apiKeyValue = "6nt5d1nJHkqbkphe"

interface ChatService {

     @GET("api/chat/")
     fun getChats(@Query("externalID") externalId : String,  @Query("message") message : String,
                  @Query("chatBotID") chatBotID : String = "63906",@Query("apiKey") apiKey : String = apiKeyValue
                    ) : LiveData<ApiResponse<ChatResponse>>

}
