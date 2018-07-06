package magdv.ivan.search.data

import com.google.gson.annotations.SerializedName

data class Repository(
        val id: Int,
        val name: String,
        @SerializedName("full_name")
        val fullName: String,
        val description: String,
        val language: String,
        @SerializedName("forks_count")
        val forksCount: Int,
        @SerializedName("stargazers_count")
        val starCount: Int,
        val owner: Owner,
        val license: License
)