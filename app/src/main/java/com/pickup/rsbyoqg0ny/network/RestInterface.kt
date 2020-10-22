package com.pickup.rsbyoqg0ny.network

import com.pickup.rsbyoqg0ny.data.model.PopularsResponse
import retrofit2.Call
import retrofit2.http.*

interface RestInterface {

    @GET("tv/popular?api_key=2906c5d56599ece166dd2f90ac831697&language=en-US")
    fun getPopular(@Query("page") page: Int): Call<PopularsResponse>
}