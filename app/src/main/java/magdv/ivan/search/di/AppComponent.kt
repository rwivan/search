package magdv.ivan.search.di

import dagger.Component
import magdv.ivan.search.adapter.RecyclerViewAdapter
import magdv.ivan.search.mvp.ListPresenter
import magdv.ivan.search.mvp.MainPresenter
import magdv.ivan.search.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NavigateModule::class, ApiModule::class))
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(listPresenter: ListPresenter)
    fun inject(recyclerViewAdapter: RecyclerViewAdapter)
}