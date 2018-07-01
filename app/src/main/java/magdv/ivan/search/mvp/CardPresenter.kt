package magdv.ivan.search.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import magdv.ivan.search.App
import magdv.ivan.search.network.api.IGitHubApi
import java.util.*
import javax.inject.Inject

@InjectViewState
class CardPresenter : MvpPresenter<CardView>() {
    @Inject
    lateinit var gitHubApi: IGitHubApi

    init {
        App.appComponent.inject(this)
    }

    fun showCard(owner: String, repo: String, license: String?) {
        gitHubApi.repos(owner, repo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewState.showRepository(it)
                }
        gitHubApi.users(owner)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    var n = it.login
                    if (null != it.name) {
                        n += " / " + it.name
                    }
                    viewState.setUserName(n)
                }
        if (null != license) {
            gitHubApi.licenses(license)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (null != it.body) {
                            val l = it.body.replace("[year]", Date().year.toString()).replace("[fullname]", owner)
                            viewState.setLicense(l)
                        }
                    }
        }
    }
}