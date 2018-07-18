package magdv.ivan.search.repo

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import magdv.ivan.search.data.Repository
import magdv.ivan.search.network.NetworkState

data class Listing(
        val pagedList: LiveData<PagedList<Repository>>,
        val networkState: LiveData<NetworkState>,
        val refreshState: LiveData<NetworkState>,
        val refresh: () -> Unit,
        val retry: () -> Unit)