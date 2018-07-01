package magdv.ivan.search.di

import android.app.Application
import dagger.Module
import dagger.Provides
import magdv.ivan.search.App
import magdv.ivan.search.network.api.IGitHubApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {
    private lateinit var client: OkHttpClient

    @Provides
    @Singleton
    fun getGitHubApi(application: Application): IGitHubApi {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(getClient(application))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(IGitHubApi::class.java)
    }

    private fun getClient(application: Application): OkHttpClient {
        if (! this::client.isInitialized) {
            synchronized(ApiModule::class.java) {
                if (! this::client.isInitialized) {
                    client = buildClient(application)
                }
            }
        }
        return client
    }

    private fun buildClient(application: Application): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val cache = Cache(application.cacheDir, 10L * 1024 * 1024)
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cache(cache)
                .build()
    }
}