package magdv.ivan.search.di

import android.app.Application
import dagger.Module
import javax.inject.Singleton
import dagger.Provides

@Module
class AppModule(private var application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return application
    }
}