package com.muraguri.comicly.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.muraguri.comicly.BuildConfig
import com.muraguri.comicly.core.data.repo.DefaultConnectivityObserver
import com.muraguri.comicly.core.remote.ComicsService
import com.muraguri.comicly.core.domain.repo.ComicRemoteSource
import com.muraguri.comicly.core.domain.repo.ConnectivityObserver
import com.muraguri.comicly.core.domain.use_cases.CoreUseCases
import com.muraguri.comicly.core.domain.use_cases.connectivity.ConnectivityObserverUseCase
import com.muraguri.comicly.core.remote.DefaultComicRemoteSource
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

interface AppDataContainer {
    val comicRemoteSource : ComicRemoteSource
    val connectivityObserver : ConnectivityObserver
    val coreUseCases : CoreUseCases
}

class DefaultAppDataContainer(
    private val context: Context
) : AppDataContainer{

    private val comicVineBaseUrl = "https://comicvine.gamespot.com/api/"

    private val json = Json { ignoreUnknownKeys = true }

    private val client : OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10L, TimeUnit.SECONDS)
        .writeTimeout(10L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .addInterceptor(provideLoggingInterceptor())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(client)
        .baseUrl(comicVineBaseUrl)
        .build()

    private val retrofitService: ComicsService by lazy {
        retrofit.create(ComicsService::class.java)
    }

    override val comicRemoteSource: ComicRemoteSource by lazy {
        DefaultComicRemoteSource(
            service = retrofitService,
            apiKey = BuildConfig.COMIC_API_KEY
        )
    }
    override val connectivityObserver: ConnectivityObserver by lazy {
        DefaultConnectivityObserver(context)
    }

    override val coreUseCases: CoreUseCases by lazy {
        CoreUseCases(
            connectivityObserverUseCases = ConnectivityObserverUseCase(connectivityObserver)
        )
    }
    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else HttpLoggingInterceptor.Level.NONE
        return HttpLoggingInterceptor().also {
            it.level = level
        }
    }
}