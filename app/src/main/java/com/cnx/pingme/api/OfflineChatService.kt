package com.cnx.pingme.api

import com.cnx.pingme.BuildConfig
import com.cnx.pingme.utils.APIKEY
import com.cnx.pingme.utils.CHATBOT_ID
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/** We are not using suspend function of kotlin like in
 * previous versions as work manager is itself executing
 * this call in background so need of couroutine calling this function anymore */

/** This apikey value can be changed to show error */

const val apiKeyValue = BuildConfig.API_DEVELOPER_TOKEN

interface OfflineChatService {

    companion object {
        const val ENDPOINT = "https://www.personalityforge.com/"
    }

    @GET("api/chat/")
    fun getChats(
        @Query("externalID") externalId: String, @Query("message") message: String,
        @Query("chatBotID") chatBotID: Int = CHATBOT_ID, @Query(APIKEY) apiKey: String = apiKeyValue
    ): Call<ChatResponse>
}
