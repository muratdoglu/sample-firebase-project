package com.mrt.firebasesample.ui.main


import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mrt.firebasesample.data.FirebaseModel
import com.mrt.firebasesample.network.*


class MainViewModel(val pref: SharedPreferences) : ViewModel() {
    val horoscope = MutableLiveData<ArrayList<FirebaseModel.Sign?>>()
    val error = MutableLiveData<String>()
    var firebaseManager = FirebaseManager()

    fun login(email: String, password: String, returnState: (Boolean) -> Unit) {
        var auth = Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    returnState(true)
                }
            }
    }

    fun getHoroscope() {
        firebaseManager.getHoroscope {
            horoscope.postValue(it)
        }
    }


}

