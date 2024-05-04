package com.muraguri.comicly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.muraguri.comicly.core.data.repo.DefaultComicRepository
import com.muraguri.comicly.core.domain.repo.ComicRemoteSource
import com.muraguri.comicly.core.domain.repo.ComicRepository
import com.muraguri.comicly.core.local.db.ComiclyDatabase
import com.muraguri.comicly.core.remote.ComicsService
import com.muraguri.comicly.core.remote.DefaultComicRemoteSource
import com.muraguri.comicly.features.home.HomeScreen
import com.muraguri.comicly.features.preference.PreferenceScreen
import com.muraguri.comicly.navigation.AppNavHost
import com.muraguri.comicly.navigation.BottomNavigationBar
import com.muraguri.comicly.navigation.Screens
import com.muraguri.comicly.ui.connectivity.ConnectivityObserverLayout
import com.muraguri.comicly.ui.theme.ComiclyTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private val comicVineBaseUrl = "https://comicvine.gamespot.com/api/"

    private val json = Json { ignoreUnknownKeys = true }

    private val client : OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10L, TimeUnit.SECONDS)
        .writeTimeout(10L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .addInterceptor(provideLoggingInterceptor())
        .addInterceptor(provideUserAgentInterceptor())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(client)
        .baseUrl(comicVineBaseUrl)
        .build()

    private val retrofitService: ComicsService by lazy {
        retrofit.create(ComicsService::class.java)
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else HttpLoggingInterceptor.Level.NONE
        return HttpLoggingInterceptor().also {
            it.level = level
        }
    }

    private fun provideUserAgentInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestWithUserAgent = originalRequest.newBuilder()
                .header("User-Agent", "mobile")
                .build()
            chain.proceed(requestWithUserAgent)
        }
    }

    private lateinit var remoteSource: ComicRemoteSource
    private lateinit var repository: ComicRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            remoteSource = DefaultComicRemoteSource(
                service = retrofitService,
                apiKey = BuildConfig.COMIC_API_KEY
            )
            repository = DefaultComicRepository(
                remoteSource = remoteSource,
                service = retrofitService,
                apiKey = BuildConfig.COMIC_API_KEY,
                favCharacterDao = ComiclyDatabase.getDatabase(this).favouriteCharacterDao()
            )

            ComiclyTheme {
                // A surface container using the 'background' color from the theme
                val scope = rememberCoroutineScope()
                ConnectivityObserverLayout {
                    ComiclyMainApp(startDestination = Screens.Home.route)
                }
            }
        }
    }
}


@Composable
fun ComiclyMainApp(startDestination : String){
    val navController = rememberNavController()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { if (bottomBarState.value) BottomNavigationBar(navController) },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            AppNavHost(
                navController = navController,
                updateBottomBarState = { bottomBarState.value = it },
                startDestination = startDestination,
            )
        }
    }
}

