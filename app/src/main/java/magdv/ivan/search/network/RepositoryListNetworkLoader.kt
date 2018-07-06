package magdv.ivan.search.network

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import magdv.ivan.search.data.IRepositoryListSource
import magdv.ivan.search.data.Repository
import magdv.ivan.search.network.api.IGitHubApi
import magdv.ivan.search.network.response.SearchResponse
import javax.inject.Inject

class RepositoryListNetworkLoader @Inject constructor(private val gitHubApi: IGitHubApi): IRepositoryListSource{
    private lateinit var searchTerm: String
    private var page = 0;
    var totalCount: Int = 0
        private set
    var isLastPage: Boolean = true
        private set
    var isLoading: Boolean = false
        private set

    override fun getFirstPageListRepository(searchTerm: String): Observable<List<Repository>> {
        this.searchTerm = searchTerm
        page = 1
        return Observable.concat(loadFromServer())
    }

    override fun loadMoreListRepository(): List<Repository> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTotalItems(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun loadFromServer() {
        isLoading = true
        gitHubApi.search(searchTerm, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { _ -> isLoading = false }
                .doAfterSuccess { _ ->
                    isLoading = false
                }

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