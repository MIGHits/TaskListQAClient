package com.example.tasklistqa.di

import com.example.tasklistqa.common.Constant.BASE_URL
import com.example.tasklistqa.data.remote.TaskService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single {
        OkHttpClient.Builder().addInterceptor(get<HttpLoggingInterceptor>())
            .readTimeout(60L, TimeUnit.SECONDS).build()
    }
    single {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }
    single { get<Retrofit>().create(TaskService::class.java) }
}