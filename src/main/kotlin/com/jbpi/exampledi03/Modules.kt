package com.jbpi.exampledi03

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val moduleMain = module {

    single { Retrofit.Builder() }

    single { OkHttpClient.Builder() }

    single {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        httpLoggingInterceptor
    }

    single {
        val okHttpClientBuilder = get() as OkHttpClient.Builder
        val httpLoggingInterceptor = get() as HttpLoggingInterceptor
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor).build()
    }

    single {
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    single {
        val retrofitBuilder = get() as Retrofit.Builder
        retrofitBuilder
            .baseUrl("https://api.twitch.tv/")
            .client((get() as OkHttpClient))
            .addConverterFactory(GsonConverterFactory.create((get() as Gson)))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single {
        val retrofit = get() as Retrofit
        retrofit.create(TwitchWebApiService::class.java)
    }

    single {
        val twitchWebApiService = get() as TwitchWebApiService
        StreamsDownloader(twitchWebApiService)
    }
}