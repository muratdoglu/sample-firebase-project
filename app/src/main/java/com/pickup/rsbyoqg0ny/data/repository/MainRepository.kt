package com.pickup.rsbyoqg0ny.data.repository

import android.util.Log
import com.pickup.rsbyoqg0ny.data.model.PopularsResponse
import com.pickup.rsbyoqg0ny.network.RestInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//hi
class MainRepository(val restInterface: RestInterface) {
    inline fun getPopulars(
        page: Int,
        crossinline successHandler: (PopularsResponse) -> Unit,
        crossinline failureHandler: (Throwable?) -> Unit,
        crossinline errorHandler: (Int) -> Unit
    ) {
        restInterface.getPopular(page).enqueue(object :
            Callback<PopularsResponse> {
            override fun onResponse(
                call: Call<PopularsResponse>?,
                response: Response<PopularsResponse>?
            ) {

                response?.let {
                    if (it.isSuccessful) {
                        successHandler(response.body())
                    } else {
                        errorHandler(it.code())
                    }
                }

            }

            override fun onFailure(call: Call<PopularsResponse>?, t: Throwable?) {
                failureHandler(t)
            }
        })
    }
}