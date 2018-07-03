package magdv.ivan.search.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import magdv.ivan.search.data.Repository

interface ListView : MvpView {
    fun clearList()
    fun showSearchResult(searchResult: List<Repository>)
    fun showEmptyResult()
    @StateStrategyType(SkipStrategy::class)
    fun showError()
}