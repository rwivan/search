package magdv.ivan.search.mvp

import android.app.Application
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import magdv.ivan.search.App
import magdv.ivan.search.di.AppComponent
import magdv.ivan.search.network.api.IGitHubApi
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    @Inject
    lateinit var gitHubApi: IGitHubApi
    init {
        App.appComponent.inject(this)
    }
    fun instantSearch(searchTerm: String) {
        viewState.activityToast(gitHubApi.search(searchTerm).toString())
    }
}