package com.example.sirius.core.di.modules

import com.example.sirius.api.ApiService
import com.example.sirius.consts.API_BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class HttpModule {
    @Provides
    @Singleton
    fun provideApiService(httpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpClientBuilder(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient.Builder = OkHttpClient.Builder().apply {
        readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
        connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
        interceptors().clear()
        addInterceptor(loggingInterceptor)
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        builder: OkHttpClient.Builder
    ): OkHttpClient = builder.build()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    companion object {
        private const val REQUEST_TIMEOUT = 30L
    }
}