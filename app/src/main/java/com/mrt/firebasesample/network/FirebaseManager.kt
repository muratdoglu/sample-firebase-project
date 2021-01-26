package com.mrt.firebasesample.network

import com.google.firebase.firestore.FirebaseFirestore
import com.mrt.firebasesample.data.FirebaseModel

class FirebaseManager(){

    var firestore: FirebaseFirestore? = null

    fun getFireStore(): FirebaseFirestore {
        if (firestore == null) {
            firestore = FirebaseFirestore.getInstance()
        }
        return firestore!!
    }

    fun getHoroscope(  returnHoroscope: ( ArrayList<FirebaseModel.Sign?>) -> Unit) {
        var horoscopeList: ArrayList<FirebaseModel.Sign?> = arrayListOf()
        getFireStore()
            .collection("Horoscope")
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot?.documents?.forEach { documentSnapshot ->
                    var sign = documentSnapshot    ?.toObject(FirebaseModel.Sign::class.java)
                    horoscopeList.add(sign)
                }
                returnHoroscope(horoscopeList)
            }
            .addOnFailureListener {
                System.out.println(it.message)
            }
    }
}