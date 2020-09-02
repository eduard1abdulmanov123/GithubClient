package abdulmanov.eduard.githubrepositories.data.network.models

import com.google.gson.annotations.SerializedName

data class OwnerNetModel(
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)