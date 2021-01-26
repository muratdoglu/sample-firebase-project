package com.mrt.firebasesample.module

import android.app.Application
import android.content.SharedPreferences
import com.mrt.firebasesample.ui.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module



val mainModule = module {
    single {
        getSharedPrefs(androidApplication())
    }
    viewModel { MainViewModel( get()) }

}
fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences(
        androidApplication.packageName,
        android.content.Context.MODE_PRIVATE
    )
}