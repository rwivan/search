package magdv.ivan.search.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.view.inputmethod.InputMethodManager
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.progress.*
import magdv.ivan.search.R
import magdv.ivan.search.adapter.PaginationScrollListener
import magdv.ivan.search.adapter.RecyclerViewAdapter
import magdv.ivan.search.data.Repository
import magdv.ivan.search.mvp.ListPresenter
import magdv.ivan.search.mvp.ListView
import org.jetbrains.anko.support.v4.alert

class ListFragment : StartFragment(), ListView {
    @InjectPresenter
    lateinit var listPresenter: ListPresenter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listPresenter.setSeachTerm(arguments?.getString("q")!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun getTotalCount(): Int = listPresenter.totalCount
            override fun isLastPage(): Boolean = listPresenter.isLastPage
            override fun isLoading(): Boolean = listPresenter.isLoading
            override fun loadMoreItems() {
                listPresenter.loadMoreItems()
            }
        })

        super.onViewCreated(view, savedInstanceState)
        recyclerView.setOnTouchListener { v, event ->
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
            searchView.clearFocus()
            false
        }
        progress.visibility = View.VISIBLE;
        emptyTextView.visibility = View.GONE;
        recyclerView.visibility = View.GONE;
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun clearList() {
        if (null != recyclerView.adapter) {
            (recyclerView.adapter as RecyclerViewAdapter).clearList()
        }
    }

    override fun showSearchResult(searchResult: List<Repository>) {
        if (null == recyclerView.adapter) {
            val list = mutableListOf<Repository>()
            list.addAll(searchResult)
            val adapter = RecyclerViewAdapter(list)
            recyclerView.setAdapter(adapter)
        } else {
            (recyclerView.adapter as RecyclerViewAdapter).removeLoadingFooter()
            (recyclerView.adapter as RecyclerViewAdapter).addAll(searchResult)
        }
        if (!listPresenter.isLastPage) {
            (recyclerView.adapter as RecyclerViewAdapter).addLoadingFooter()
        }
    }

    override fun showEmptyResult() {
        emptyTextView.setText(R.string.empty)
    }

    override fun showError() {
        alert(R.string.error).show()
    }

    override fun visibleProgress() {
        progress.visibility = View.VISIBLE;
        emptyTextView.visibility = View.GONE;
        recyclerView.visibility = View.GONE;
    }

    override fun visibleList() {
        progress.visibility = View.GONE;
        emptyTextView.visibility = View.GONE;
        recyclerView.visibility = View.VISIBLE;
    }

    override fun visibleEmptyText() {
        progress.visibility = View.GONE;
        emptyTextView.visibility = View.VISIBLE;
        recyclerView.visibility = View.GONE;
    }

    override fun setSearchViewText(query: String) {
        //searchView.setQuery(query, false)
    }
}
