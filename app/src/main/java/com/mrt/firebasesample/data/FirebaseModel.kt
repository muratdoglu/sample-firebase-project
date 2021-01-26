package com.mrt.firebasesample.data

import android.os.Parcel
import android.os.Parcelable


class FirebaseModel() : Parcelable {


    constructor(parcel: Parcel) : this() {
    }

    class Sign() {
        var id:Int?=0
        var name:String?=""
        var imageUrl:String = ""
        var description:String=""
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FirebaseModel> {
        override fun createFromParcel(parcel: Parcel): FirebaseModel {
            return FirebaseModel(parcel)
        }

        override fun newArray(size: Int): Array<FirebaseModel?> {
            return arrayOfNulls(size)
        }
    }
}