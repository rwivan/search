package magdv.ivan.search.ui

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.*
import com.arellomobile.mvp.MvpAppCompatFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.progress.*
import magdv.ivan.search.App
import magdv.ivan.search.R
import magdv.ivan.search.Screen
import org.jetbrains.anko.editText
import ru.terrakok.cicerone.Router
import java.util.concurrent.TimeUnit
import javax.inject.Inject

open class StartFragment : MvpAppCompatFragment() {
    lateinit var searchView: SearchView
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyTextView.setText(R.string.search)
        progress.visibility = View.GONE;
        emptyTextView.visibility = View.VISIBLE;
        recyclerView.visibility = View.GONE;
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
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
                    router.newRootScreen(Screen.LIST_SCREEN, it)
                }
        searchView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    searchView.onActionViewCollapsed();
                    return true;
                }
                return false
            }
        })
        searchView.setOnCloseListener {
            router.exit()
            true
        }
        return super.onCreateOptionsMenu(menu, inflater)
    }
}