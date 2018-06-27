package magdv.ivan.search.di

import dagger.Module
import dagger.Provides
import magdv.ivan.search.mvp.ListPresenter
import javax.inject.Singleton

@Module
class MvpModule {
    @Provides
    @Singleton
    fun getListPresenter(): ListPresenter
    {
        return ListPresenter()
    }
}