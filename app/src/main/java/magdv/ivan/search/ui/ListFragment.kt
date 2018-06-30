package magdv.ivan.search.ui

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import kotlinx.android.synthetic.main.fragment_list.*
import magdv.ivan.search.R
import magdv.ivan.search.adapter.PaginationScrollListener
import magdv.ivan.search.adapter.RecyclerViewAdapter
import magdv.ivan.search.data.Repository
import magdv.ivan.search.mvp.ListPresenter
import magdv.ivan.search.mvp.ListView
import magdv.ivan.search.network.api.IGitHubApi
import magdv.ivan.search.network.response.SearchResponse
import org.jetbrains.anko.support.v4.toast
import kotlin.math.ceil


class ListFragment : MvpAppCompatFragment(), ListView {
    @InjectPresenter
    lateinit var listPresenter: ListPresenter;
    lateinit var searchResponse: SearchResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_list, container, false)
        listPresenter.instantSearch(arguments?.getString("q")!!)
        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun getTotalCount():Int {
                return listPresenter.totalCount
            }

            override fun isLastPage(): Boolean {
                return listPresenter.isLastPage
            }

            override fun isLoading(): Boolean {
                return listPresenter.isLoading
            }

            override fun loadMoreItems() {
                listPresenter.loadMoreItems()
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }

    override fun showSearchResult(searchResult: MutableList<Repository>)
    {
        if (null == recyclerView.adapter) {
            val adapter = RecyclerViewAdapter(searchResult)
            recyclerView.setAdapter(adapter)
        } else {
            (recyclerView.adapter as RecyclerViewAdapter).addAll(searchResult)
        }
    }

    override fun addLoadingFooter() {
        if (null != recyclerView.adapter) {
            (recyclerView.adapter as RecyclerViewAdapter).addLoadingFooter()
        }
    }

    override fun removeLoadingFooter() {
        if (null != recyclerView.adapter) {
            (recyclerView.adapter as RecyclerViewAdapter).removeLoadingFooter()
        }
    }

    override fun activityToast2(string: String) {
        toast(string)
    }
}
