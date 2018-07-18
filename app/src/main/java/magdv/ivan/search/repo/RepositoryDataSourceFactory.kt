package magdv.ivan.search.repo

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import magdv.ivan.search.data.Repository
import magdv.ivan.search.network.api.IGitHubApi
import java.util.concurrent.Executor

class RepositoryDataSourceFactory (
        private val gitHubApi: IGitHubApi,
        private val query: String,
        private val retryExecutor: Executor) : DataSource.Factory<Int, Repository>() {

    val sourceLiveData = MutableLiveData<PageKeyedRepositoryDataSource>()
    override fun create(): DataSource<Int, Repository> {
        val source = PageKeyedRepositoryDataSource(gitHubApi, query, retryExecutor)
        sourceLiveData.postValue(source)
        return source
    }
}
