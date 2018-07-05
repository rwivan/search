package magdv.ivan.search.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import magdv.ivan.search.App
import magdv.ivan.search.R
import magdv.ivan.search.Screen
import magdv.ivan.search.mvp.MainPresenter
import magdv.ivan.search.mvp.MainView
import org.jetbrains.anko.support.v4.withArguments
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity(), MainView {
    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter;
    @Inject
    lateinit var router: Router
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        searchView = menu.findItem(R.id.action_search)?.actionView as SearchView
        Observable.create<String>() {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    it.onNext(newText.toString())
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean = false
            })
        }.debounce(500, TimeUnit.MILLISECONDS)
                .map { it.toString() }
                .filter { it.length > 1 }
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mainPresenter.showList(it.toString())
                }
        searchView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v:View?, keyCode: Int, event: KeyEvent?) : Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    searchView.onActionViewCollapsed();
                    return true;
                }
                return false
            }
        })
        searchView.setOnCloseListener {
            mainPresenter.backToList()
            false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        val navigator: Navigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.main_container) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment {
                when (screenKey) {
                    Screen.CARD_SCREEN -> return CardFragment().withArguments(Pair("repository", data))
                    else -> return ListFragment().withArguments(Pair("q", data.toString()))
                }
            }

            override fun exit() {
                finish()
            }

            override fun showSystemMessage(message: String?) = Unit
        }
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mainPresenter.backToList()
    }

    fun searchViewClearFocus()
    {
        searchView.clearFocus()
    }
}
