package magdv.ivan.search.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import magdv.ivan.search.App
import magdv.ivan.search.Screen
import magdv.ivan.search.network.api.IGitHubApi
import magdv.ivan.search.network.response.SearchResponse
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ListPresenter : MvpPresenter<ListView>() {
    @Inject
    lateinit var gitHubApi: IGitHubApi
    @Inject
    lateinit var router: Router

    init {
        App.appComponent.inject(this)
    }

    fun instantSearch(searchTerm: String) {
        router.navigateTo(Screen.LIST_SCREEN)
        gitHubApi.search(searchTerm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<SearchResponse> {
                    override fun onComplete() {
                        viewState.activityToast2("конец")
                    }

                    override fun onSubscribe(d: Disposable) {
                        viewState.activityToast2("кто-то подписался")
                    }

                    override fun onNext(t: SearchResponse) {
                        viewState.showSearchResult(t)
                        viewState.activityToast2("дальше")
                        viewState.activityToast2(t.total_count.toString())
                    }

                    override fun onError(e: Throwable) {
                        viewState.activityToast2("ошибка")
                    }
                })
    }
}