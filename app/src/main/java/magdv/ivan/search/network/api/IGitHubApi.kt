package magdv.ivan.search.network.api

import io.reactivex.Observable
import magdv.ivan.search.data.License
import magdv.ivan.search.data.Repository
import magdv.ivan.search.data.User
import magdv.ivan.search.network.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface IGitHubApi {
    companion object {
        val PER_PAGE = 2
    }

    @Headers(
            "Accept: application/vnd.github.v3.text-match+json",
            "User-Agent: My-App-GitHub-Search"
    )
    @GET("search/repositories?sort=stars&order=desc&per_page=2")
    abstract fun search(
            @Query("q") q: String,
            @Query("page") page: Int
    ): Observable<SearchResponse>

    @Headers(
            "Accept: application/vnd.github.v3.text-match+json",
            "User-Agent: My-App-GitHub-Search"
    )
    @GET("repos/{owner}/{repo}")
    abstract fun repos(
            @Path("owner") owner: String,
            @Path("repo") repo: String
    ): Observable<Repository>

    @Headers(
            "Accept: application/vnd.github.v3.text-match+json",
            "User-Agent: My-App-GitHub-Search"
    )
    @GET("users/{login}")
    abstract fun users(@Path(value = "login") login: String): Observable<User>

    @Headers(
            "Accept: application/vnd.github.v3.text-match+json",
            "User-Agent: My-App-GitHub-Search"
    )
    @GET("licenses/{name}")
    abstract fun licenses(@Path(value = "name") name: String): Observable<License>

}
