package magdv.ivan.search.repo

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.support.annotation.MainThread
import magdv.ivan.search.network.api.IGitHubApi
import java.util.concurrent.Executor

class RepositoryRepo(private val gitHubApi: IGitHubApi,
                     private val networkExecutor: Executor) {
    @MainThread
    fun loadRepositoryByQuery(query: String, pageSize: Int): Listing {
        val sourceFactory = RepositoryDataSourceFactory(gitHubApi, query, networkExecutor)

        val livePagedList = LivePagedListBuilder(sourceFactory, pageSize)
                .setFetchExecutor(networkExecutor)
                .build()

        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }
        return Listing(
                pagedList = livePagedList,
                networkState = Transformations.switchMap(sourceFactory.sourceLiveData, {
                    it.networkState
                }),
                retry = {
                    sourceFactory.sourceLiveData.value?.retryAllFailed()
                },
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                },
                refreshState = refreshState
        )
    }
}