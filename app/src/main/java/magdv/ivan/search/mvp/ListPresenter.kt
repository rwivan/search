package magdv.ivan.search.mvp

import android.view.View
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
class ListPresenter : MvpPresenter<ListView>() {
    @Inject
    lateinit var gitHubApi: IGitHubApi
    var totalCount: Int = 0
        private set
    var isLastPage: Boolean = true
        private set
    var isLoading: Boolean = false
        private set
    private var searchTerm: String = ""
    private var page: Int = 0

    init {
        App.appComponent.inject(this)
    }

    fun setSeachTerm(q: String) {
        searchTerm = q
    }

    override fun onFirstViewAttach() {
        viewState.visibleProgress()
        viewState.clearList()
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
                        totalCount = t.totalCount
                        if (totalCount > 0) {
                            viewState.visibleList()
                            isLastPage = page >= totalCount / IGitHubApi.PER_PAGE
                            viewState.showSearchResult(t.items)
                        } else {
                            viewState.visibleEmptyText()
                            viewState.showEmptyResult()
                        }
                    }

                    override fun onError(e: Throwable) {
                        isLoading = false
                        viewState.showError()
                    }
                })
    }
}