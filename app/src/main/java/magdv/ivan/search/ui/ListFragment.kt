package magdv.ivan.search.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_list.*
import magdv.ivan.search.R
import magdv.ivan.search.adapter.PaginationScrollListener
import magdv.ivan.search.adapter.RecyclerViewAdapter
import magdv.ivan.search.data.Repository
import magdv.ivan.search.mvp.ListPresenter
import magdv.ivan.search.mvp.ListView


class ListFragment : MvpAppCompatFragment(), ListView {
    @InjectPresenter
    lateinit var listPresenter: ListPresenter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listPresenter.instantSearch(arguments?.getString("q")!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun getTotalCount(): Int {
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

    override fun clearList() {
        if (null != recyclerView.adapter) {
            (recyclerView.adapter as RecyclerViewAdapter).clearList()
        }
    }

    override fun showSearchResult(searchResult: List<Repository>) {
        progress.visibility = View.GONE
        if (null == recyclerView.adapter) {
            val list = mutableListOf<Repository>()
            list.addAll(searchResult)
            val adapter = RecyclerViewAdapter(list)
            recyclerView.setAdapter(adapter)
        } else {
            (recyclerView.adapter as RecyclerViewAdapter).removeLoadingFooter()
            (recyclerView.adapter as RecyclerViewAdapter).addAll(searchResult)
        }
        (recyclerView.adapter as RecyclerViewAdapter).addLoadingFooter()
    }
}
