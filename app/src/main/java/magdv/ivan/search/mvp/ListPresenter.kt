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

    override fun onFirstViewAttach() {

    }

    fun instantSearch(q: String) {
        viewState.clearList()
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
                    }

                    override fun onSubscribe(d: Disposable) = Unit

                    override fun onNext(t: SearchResponse) {
                        totalCount = t.total_count
                        if (totalCount > 0) {
                            isLastPage = page >= totalCount / IGitHubApi.PER_PAGE
                            viewState.showSearchResult(t.items)
                        } else {
                            router.newRootScreen(Screen.ERROR_SCREEN, Screen.ERROR_SCREEN_TYPE_EMPTY)
                        }
                    }

                    override fun onError(e: Throwable) {
                        isLoading = false
                        router.newRootScreen(Screen.ERROR_SCREEN, Screen.ERROR_SCREEN_TYPE_ERROR)
                    }
                })
    }
}