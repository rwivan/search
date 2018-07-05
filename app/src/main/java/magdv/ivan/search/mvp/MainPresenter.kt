package magdv.ivan.search.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import magdv.ivan.search.App
import magdv.ivan.search.Screen
import magdv.ivan.search.network.api.IGitHubApi
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    @Inject
    lateinit var router: Router

    init {
        App.appComponent.inject(this)
    }

    fun showList(searchTerm: String) {
        router.newRootScreen(Screen.LIST_SCREEN, searchTerm)
    }

    fun backToList() {
        router.backTo(Screen.LIST_SCREEN)
    }
}