package com.pickup.rsbyoqg0ny.module

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import com.pickup.rsbyoqg0ny.data.repository.MainRepository
import com.pickup.rsbyoqg0ny.network.RestInterface
import com.pickup.rsbyoqg0ny.ui.main.MainViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val mainModule = module {
    single {
        getSharedPrefs(androidApplication())
    }
    single { MainRepository(get()) }
    viewModel { MainViewModel(get(), get()) }

}

val networkModule = module {
    factory<Interceptor> {
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Log.d("API", it) })
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    factory {
        OkHttpClient.Builder().protocols(arrayListOf(Protocol.HTTP_1_1)).addInterceptor(get())
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://api.themoviedb.org/3/")

            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory { get<Retrofit>().create(RestInterface::class.java) }

}

fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences(
        androidApplication.packageName,
        android.content.Context.MODE_PRIVATE
    )
}