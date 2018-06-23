package magdv.ivan.search.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    init{
//        observable = Observable.create<String>(object : ObservableOnSubscribe<String> {
//            override fun subscribe(emitter: ObservableEmitter<String>) {
//                emitter.onNext("fdd");
//            }
//        })
//        observable
//                .debounce(500, TimeUnit.MILLISECONDS)
//                .map { it.toString() }
//                .filter { it.length > 2 }
//                .distinctUntilChanged()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    i++
//                    viewState.activityToast(i.toString())
//                    //textView.setText(it.toString())
//                }
//
    }
    lateinit var observable : Disposable
    var i = 0

    fun instantSearch(searchTerm: String) {
       //  val observable: Observable = Observable.create()
        //ObservablePublish<String>.c
        if (! this::observable.isInitialized) {
            observable = Observable.create<String> {
                it.onNext(searchTerm)
            }
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .map { it.toString() }
                    .filter { it.length > 2 }
                    .distinctUntilChanged()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        i++
                        viewState.activityToast(i.toString())
                        //textView.setText(it.toString())
                    }
        }
       // observable.ne
        // mAdapter.getFilter().filter(newText)

//        RxSearchView.queryTextChangeEvents(searchView)
//                .debounce(200, TimeUnit.MILLISECONDS) // Better store the value in a constant like Constant.DEBOUNCE_SEARCH_REQUEST_TIMEOUT
//
//                .distinctUntilChanged()
//                .switchMap(new Function<String, ObservableSource<String>>() {
//                    @Override
//                    public ObservableSource<String> apply(String query) throws Exception {
//                        return dataFromNetwork(query);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    //textView.setText(it.toString())
//                }
    }
}