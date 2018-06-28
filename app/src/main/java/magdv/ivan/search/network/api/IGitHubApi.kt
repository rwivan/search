package magdv.ivan.search.network.api

import io.reactivex.Observable
import magdv.ivan.search.data.Repository
import magdv.ivan.search.network.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface IGitHubApi {
    companion object {
        val PER_PAGE = 100
    }

    @Headers(
            "Accept: application/vnd.github.v3.text-match+json",
            "User-Agent: My-App-GitHub-Search"
    )
    @GET("search/repositories?sort=stars&order=desc&per_page=100")
    abstract fun search(
            @Query("q") q: String,
            @Query("p") p: Int
    ): Observable<SearchResponse>

    @GET("stat")
    abstract fun stat(name: String): Observable<Repository>
}
