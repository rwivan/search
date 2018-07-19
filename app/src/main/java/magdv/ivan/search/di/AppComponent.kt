package magdv.ivan.search.di

import dagger.Component
import magdv.ivan.search.adapter.RecyclerViewAdapter
import magdv.ivan.search.mvp.CardPresenter
import magdv.ivan.search.mvp.ListPresenter
import magdv.ivan.search.ui.ListFragment
import magdv.ivan.search.ui.MainActivity
import magdv.ivan.search.ui.StartFragment
import magdv.ivan.search.ui.RepositoryViewHolder
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NavigateModule::class, ApiModule::class, AppModule::class))
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(listPresenter: ListPresenter)
    fun inject(listFragment: ListFragment)
    fun inject(startFragment: StartFragment)
    fun inject(recyclerViewAdapter: RecyclerViewAdapter)
    fun inject(cardPresenter: CardPresenter)
    fun inject(repositoryViewHolder: RepositoryViewHolder)
}