package magdv.ivan.search.mvp

import com.arellomobile.mvp.MvpView
import magdv.ivan.search.data.Repository

interface ListView : MvpView {
    fun clearList()
    fun showSearchResult(searchResult: List<Repository>)
    fun showEmptyResult()
}