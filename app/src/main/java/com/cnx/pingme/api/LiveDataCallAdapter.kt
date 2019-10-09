package com.cnx.pingme.api


import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A Retrofit adapter that converts the Call into a LiveData of ApiResponse.
 * Again for one weather api call it is not required
 * I am doing this to show how convert all network calls directly in live data in an app
 * @param <R>
</R> */
class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {


        return object : LiveData<ApiResponse<R>>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {

                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {

                        override fun onResponse(call: Call<R>, response: Response<R>) {

                            postValue(ApiResponse.createSuccessResponse(response))
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            postValue(ApiResponse.createErrorResponse(throwable))
                        }
                    })
                }
            }
        }
    }
}
