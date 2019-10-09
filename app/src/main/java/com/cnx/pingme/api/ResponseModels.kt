package com.cnx.pingme.api
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




data class ErrorModel(@Expose @SerializedName("message") var errMsg : String)

data class ChatResponse(
    @SerializedName("data")
    var `data`: List<Message?>?,
    @SerializedName("errorMessage")
    var errorMessage: String?,
    @SerializedName("message")
    var message: Message?,
    @SerializedName("success")
    var success: Int
)

data class Message(
    @SerializedName("chatBotID")
    var chatBotID: Int?,
    @SerializedName("chatBotName")
    var chatBotName: String? = "",
    @SerializedName("emotion")
    var emotion: String?,
    @SerializedName("message")
    var message: String? = "",
    var isSent : Boolean = false
)