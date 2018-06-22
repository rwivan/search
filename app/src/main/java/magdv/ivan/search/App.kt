package magdv.ivan.search

import android.app.Application
import magdv.ivan.search.di.AppComponent
import magdv.ivan.search.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }
}