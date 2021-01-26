package com.mrt.firebasesample

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.mrt.firebasesample.module.mainModule

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf( mainModule))
        }
         var firebaseUser = FirebaseAuth.getInstance().currentUser
    }
}