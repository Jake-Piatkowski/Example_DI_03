package com.jbpi.exampledi03

import com.jbpi.exampledi03.providers.*
import org.koin.dsl.module.module

val moduleMain = module {
    single { RetrofitBuilderProvider() }
    single { OkHttpClientBuilderProvider() }
    single { HttpLoggingInterceptorProvider() }
    single { OkHttpClientProvider(
        (get() as OkHttpClientBuilderProvider).provideOkHttpClientBuilder(),
        (get() as HttpLoggingInterceptorProvider).provideHttpLoggingInterceptor()
    ) }
    single { GsonProvider() }
    single { RetrofitProvider(
        (get() as RetrofitBuilderProvider).provideRetrofitBuilder(),
        (get() as OkHttpClientProvider).provideOkHttpClient(),
        (get() as GsonProvider).provideGson()
    ) }
    single { TwitchWebApiServiceProvider(
        (get() as RetrofitProvider).provideRetrofit()
    ) }
    single { StreamsDownloader(
        (get() as TwitchWebApiServiceProvider).provideTwitchWebApiService()
    ) }
}