package magdv.ivan.search.network.response

import com.google.gson.annotations.SerializedName
import magdv.ivan.search.data.Repository

data class SearchResponse(
        @SerializedName("total_count")
        val totalCount: Int,
        val items: List<Repository>
)