package com.pickup.rsbyoqg0ny.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pickup.rsbyoqg0ny.data.model.Movie
import com.pickup.rsbyoqg0ny.data.model.PopularsResponse
import com.pickup.rsbyoqg0ny.data.repository.MainRepository

class MainViewModel(val repo: MainRepository, val pref: SharedPreferences) : ViewModel() {

    val popularsResponse = MutableLiveData<PopularsResponse>()

    val popularMovies = MutableLiveData<ArrayList<Movie>>()

    fun getPopulars(page: Int) {
        repo.getPopulars(page, {
            popularsResponse.postValue(it)
        }, {

        }, {

        })
    }
}