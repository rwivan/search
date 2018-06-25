package magdv.ivan.search.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import magdv.ivan.search.App
import magdv.ivan.search.network.api.IGitHubApi
import magdv.ivan.search.network.response.SearchResponse
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    @Inject
    lateinit var gitHubApi: IGitHubApi

    init {
        App.appComponent.inject(this)
    }

    fun instantSearch(searchTerm: String) {
        gitHubApi.search(searchTerm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<SearchResponse> {
                    override fun onComplete() {
                        viewState.activityToast("конец")
                    }

                    override fun onSubscribe(d: Disposable) {
                        viewState.activityToast("кто-то подписался")
                    }

                    override fun onNext(t: SearchResponse) {
                        viewState.activityToast("дальше")
                        viewState.activityToast(t.total_count.toString())
                    }

                    override fun onError(e: Throwable) {
                        viewState.activityToast("ошибка")
                    }
                })
    }
}