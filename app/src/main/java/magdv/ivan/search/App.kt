package magdv.ivan.search

import android.app.Application
import magdv.ivan.search.di.AppComponent
import magdv.ivan.search.di.DaggerAppComponent

class App : Application() {

    companion object {
        val appComponent: AppComponent by lazy {
            DaggerAppComponent.builder().build()
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}