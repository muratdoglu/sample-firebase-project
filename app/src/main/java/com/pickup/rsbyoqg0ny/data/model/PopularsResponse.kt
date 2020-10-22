package com.pickup.rsbyoqg0ny.data.model

data class PopularsResponse(
    val page: Int,
    var results: ArrayList<Movie> = arrayListOf()
)
