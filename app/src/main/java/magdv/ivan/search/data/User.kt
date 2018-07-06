package magdv.ivan.search.data

import com.google.gson.annotations.SerializedName
import javax.security.auth.login.LoginException

data class User (
        val login: String,
        @SerializedName("avatar_url")
        val avatarUrl: String,
        val name: String?
)