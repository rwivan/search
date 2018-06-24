package magdv.ivan.search.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    fun instantSearch(searchTerm: String) {
        viewState.activityToast(searchTerm)
    }
}