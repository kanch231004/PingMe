package com.cnx.pingme.api
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class ErrorModel(@Expose @SerializedName("messageModel") var errMsg : String)

data class ChatResponse(
    @SerializedName("errorMessage")
    var errorMessage: String?,
    @SerializedName("message")
    var messageModel: MessageModel?,
    @SerializedName("success")
    var success: Int
)

@Entity
data class MessageModel(

    @PrimaryKey
    var id : String ,
    var userSession : String,

    @SerializedName("chatBotID")
    var chatBotID: Int?,
    @SerializedName("chatBotName")
    var chatBotName: String?,
    @SerializedName("emotion")
    var emotion: String?,
    @SerializedName("message")
    var message: String?,
    var isSent : Boolean = false,
    var isSuccess : Boolean = true
)