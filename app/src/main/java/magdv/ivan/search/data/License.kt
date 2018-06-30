package magdv.ivan.search.data

data class License (
    val key : String,
    val name : String,
    val spdx_id : String,
    val url : String,
    val body: String?){
}