package magdv.ivan.search.di

import dagger.Component
import magdv.ivan.search.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NavigateModule::class))
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}