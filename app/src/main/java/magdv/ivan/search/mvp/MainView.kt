package magdv.ivan.search.mvp

import com.arellomobile.mvp.MvpView

interface MainView : MvpView {
    fun activityToast(string: String)
}