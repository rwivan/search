package magdv.ivan.search.mvp

import com.arellomobile.mvp.MvpView
import magdv.ivan.search.data.Repository

interface CardView : MvpView {
    fun showRepository(repository: Repository)
    fun setUserName(name: String)
    fun setLicense(body: String)
    fun visibleCard()
}