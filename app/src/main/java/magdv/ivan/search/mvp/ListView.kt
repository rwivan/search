package magdv.ivan.search.mvp

import com.arellomobile.mvp.MvpView
import magdv.ivan.search.network.response.SearchResponse

interface ListView : MvpView {
    fun activityToast2(string: String)
    fun showSearchResult(searchResult: SearchResponse)
}