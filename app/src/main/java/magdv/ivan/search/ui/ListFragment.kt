package magdv.ivan.search.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import magdv.ivan.search.App

import magdv.ivan.search.R
import magdv.ivan.search.mvp.ListPresenter
import magdv.ivan.search.mvp.ListView
import magdv.ivan.search.mvp.MainPresenter
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.PresenterType
import kotlinx.android.synthetic.main.fragment_list.*
import magdv.ivan.search.adapter.RecyclerViewAdapter
import magdv.ivan.search.data.License
import magdv.ivan.search.data.Owner
import magdv.ivan.search.data.Repository
import magdv.ivan.search.network.response.SearchResponse
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast


class ListFragment : MvpAppCompatFragment(), ListView {
    @InjectPresenter(type=PresenterType.GLOBAL)
    lateinit var listPresenter: ListPresenter;
    lateinit var searchResponse: SearchResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_list, container, false)
        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.setLayoutManager(layoutManager)
        searchResponse = SearchResponse(0, listOf<Repository>())
        val adapter = RecyclerViewAdapter(searchResponse)
        // Repository(1, "ds", "dddd", "wqwq", "ws", 12, 21, Owner("sds", "ewew"), License("fd", "fdf", "ewew", "rere"))
        recyclerView.setAdapter(adapter)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun showSearchResult(searchResult: SearchResponse)
    {
        val adapter = RecyclerViewAdapter(searchResult)
        recyclerView.setAdapter(adapter)
        adapter.notifyDataSetChanged()
    }

    override fun activityToast2(string: String) {
        toast(string)
    }
}
