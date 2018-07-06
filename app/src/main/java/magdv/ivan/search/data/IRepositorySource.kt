package magdv.ivan.search.data

interface IRepositorySource {
    fun getRepository(owner: String, repositoryName: String)
}