package magdv.ivan.search.di

import dagger.Module
import dagger.Provides
import magdv.ivan.search.network.api.IGitHubApi
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
    fun getGitHubApi(): IGitHubApi {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(IGitHubApi::class.java)
    }

    private fun getClient(): OkHttpClient {
        if (! this::client.isInitialized) {
            synchronized(ApiModule::class.java) {
                if (! this::client.isInitialized) {
                    client = buildClient()
                }
            }
        }
        return client
    }

    private fun buildClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
    }
}