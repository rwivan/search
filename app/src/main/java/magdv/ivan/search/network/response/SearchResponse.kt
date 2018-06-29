package magdv.ivan.search.network.response

import magdv.ivan.search.data.Repository

data class SearchResponse (
        val total_count : Int,
        val items : MutableList<Repository>
) {
}