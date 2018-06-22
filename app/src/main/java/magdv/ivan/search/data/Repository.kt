package magdv.ivan.search.data

data class Repository(
        val id: Int,
        val name: String,
        val full_name: String,
        val description: String,
        val language: String,
        val forks_count: String,
        val stargazers_count: Int,
        val owner: Owner,
        val license: License
) {
}