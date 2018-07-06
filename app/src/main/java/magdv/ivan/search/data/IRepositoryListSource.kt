package magdv.ivan.search.data

import io.reactivex.Observable

interface IRepositoryListSource {
    fun getListRepository(searchTerm: String): Observable<List<Repository>>
    fun getTotalItems(): Int
}