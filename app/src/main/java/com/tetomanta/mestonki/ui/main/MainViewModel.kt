package com.tetomanta.mestonki.ui.main


import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tetomanta.mestonki.network.DataSource
import com.tetomanta.mestonki.network.FetchError
import com.tetomanta.mestonki.network.FetchResponse
import com.tetomanta.mestonki.network.Person


class MainViewModel(val pref: SharedPreferences) : ViewModel() {
    val people = MutableLiveData<MutableList<Person>>()
    val error = MutableLiveData<String>()
    var next: String? = null
    var dataSource = DataSource()

    fun getPeoplePart() {
        dataSource.fetch(next) { fetchResponse: FetchResponse?, fetchError: FetchError? ->
            if (fetchError == null) {
                next = fetchResponse?.next
                var cachePeople = fetchResponse?.people?.toMutableList()
                if (cachePeople?.size ?: 0 > 0) {
                    var oldPeople: MutableList<Person>? = people.value?.toMutableList()
                    cachePeople?.let { oldPeople?.addAll(it) }
                    people.postValue(oldPeople?.distinct()?.toMutableList())
                } else {
                    getPeoplePart()
                }

            } else {
                error.postValue(fetchError.errorDescription)
            }

        }
    }


}

