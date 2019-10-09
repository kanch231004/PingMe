package com.cnx.pingme.api

import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.UnknownHostException

/** It is written just to showcase proper architecture,
 * All apis will get end result in the form of live data
 * Data redundancy will be avoided. Like weather api call, all api calls will be inheriting this common response
 * Hence avoiding null response by error handling all at one place.
 * Common class used by all API responses.
 * @param <T> the type of the response object
</T> */

sealed class ApiResponse<T> {

    private val builder = GsonBuilder().setPrettyPrinting().serializeNulls()
    private val gson = builder.create()

    companion object {

        fun <T> createErrorResponse(throwable: Throwable): ApiResponse<T> {
            throwable.printStackTrace()
            var message = ""
            if (throwable is UnknownHostException)
                message = "Check Internet Connection!"
            return ApiErrorResponse(if (message.isNotEmpty()) message else "Something went wrong at our end!")

        }

        fun <T> createSuccessResponse(response: Response<T>): ApiResponse<T> {

            return if (response.isSuccessful) {

                val body = response.body()
                if (body == null || response.code() == 204) {

                    ApiEmptyResponse()

                } else {

                    ApiSuccessResponse(body = body)
                }
            } else {

                val msg = response.errorBody()?.deserialize<ErrorModel>()?.errMsg
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(if (!errorMsg.isNullOrEmpty()) errorMsg else "Something went wrong at our end!" )
            }

        }
    }


}

class ApiEmptyResponse<T> : ApiResponse<T>()
data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()
data class ApiSuccessResponse<T> (val body : T) : ApiResponse<T>()

inline fun <reified T> ResponseBody.deserialize() : T {

    val builder = GsonBuilder().setPrettyPrinting().serializeNulls()
    val gson = builder.create()
    return gson.fromJson(this.string(),T::class.java)

}
