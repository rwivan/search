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
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ListPresenter : MvpPresenter<ListView>() {
    @Inject
    lateinit var gitHubApi: IGitHubApi
    @Inject
    lateinit var router: Router
    var totalCount: Int = 0
        private set
    var isLastPage: Boolean = true
        private set
    var isLoading: Boolean = false
        private set
    private lateinit var searchTerm: String
    private var page: Int = 0

    init {
        App.appComponent.inject(this)
    }

    fun instantSearch(q: String) {
        searchTerm = q
        page = 1
        load()
    }

    fun loadMoreItems() {
        page++
        load()
    }

    private fun load() {
        isLoading = true
        gitHubApi.search(searchTerm, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<SearchResponse> {
                    override fun onComplete() {
                        isLoading = false
                        //viewState.activityToast2("конец")
                    }

                    override fun onSubscribe(d: Disposable) {
                        //viewState.activityToast2("кто-то подписался")
                    }

                    override fun onNext(t: SearchResponse) {
                        totalCount = t.total_count
                        isLastPage = page > totalCount / IGitHubApi.PER_PAGE
                        viewState.showSearchResult(t.items)
                        viewState.activityToast2("дальше")
                        viewState.activityToast2(t.total_count.toString())
                    }

                    override fun onError(e: Throwable) {
                        viewState.activityToast2("ошибка")
                    }
                })
    }
}