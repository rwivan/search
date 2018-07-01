package magdv.ivan.search

import android.app.Application
import magdv.ivan.search.di.AppComponent
import magdv.ivan.search.di.AppModule
import magdv.ivan.search.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()

    }
}