package magdv.ivan.search.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import magdv.ivan.search.App
import magdv.ivan.search.data.License
import magdv.ivan.search.data.Repository
import magdv.ivan.search.data.User
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
                .subscribe(object : Observer<Repository> {
                    override fun onComplete() = Unit
                    override fun onSubscribe(d: Disposable) = Unit
                    override fun onError(e: Throwable) = Unit
                    override fun onNext(t: Repository) {
                        viewState.showRepository(t)
                    }
                })
        gitHubApi.users(owner)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<User> {
                    override fun onComplete() = Unit
                    override fun onSubscribe(d: Disposable) = Unit
                    override fun onError(e: Throwable) = Unit
                    override fun onNext(t: User) {
                        var n = t.login
                        if (null != t.name) {
                            n += " / " + t.name
                        }
                        viewState.setUserName(n)
                    }
                })
        if (null != license) {
            gitHubApi.licenses(license)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<License> {
                        override fun onComplete() = Unit
                        override fun onSubscribe(d: Disposable) = Unit
                        override fun onError(e: Throwable) = Unit
                        override fun onNext(t: License) {
                            if (null != t.body) {
                                val l = t.body.replace("[year]", Date().year.toString()).replace("[fullname]", owner)
                                viewState.setLicense(l)
                            }
                        }
                    })
        }
    }
}