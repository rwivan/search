package magdv.ivan.search.repo

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import magdv.ivan.search.data.Repository
import magdv.ivan.search.network.NetworkState
import magdv.ivan.search.network.api.IGitHubApi
import magdv.ivan.search.network.response.SearchResponse
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor

class PageKeyedRepositoryDataSource(
        private val gitHubApi: IGitHubApi,
        private val query: String,
        private val retryExecutor: Executor) : PageKeyedDataSource<Int, Repository>() {

    private var retry: (() -> Any)? = null

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, Repository>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {
        networkState.postValue(NetworkState.LOADING)
        gitHubApi.searchCall(q = query, page = params.key)
                .enqueue(
                        object : retrofit2.Callback<SearchResponse> {
                            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                                retry = {
                                    loadAfter(params, callback)
                                }
                                networkState.postValue(NetworkState.error(t.message
                                        ?: "unknown err"))
                            }

                            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                                if (response.isSuccessful) {
                                    val items = response.body()?.items ?: emptyList()
                                    retry = null
                                    callback.onResult(items, params.key + 1)
                                    networkState.postValue(NetworkState.LOADED)
                                } else {
                                    retry = {
                                        loadAfter(params, callback)
                                    }
                                    networkState.postValue(NetworkState.error("error code: ${response.code()}"))
                                }
                            }
                        }
                )
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Repository>) {
        val request = gitHubApi.searchCall(q = query, page = 1)
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        // triggered by a refresh, we better execute sync
        try {
            val response = request.execute()
            val items = response.body()?.items ?: emptyList()
            retry = null
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(items, null, 2)
        } catch (ioException: IOException) {
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(ioException.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }
}