package abdulmanov.eduard.githubrepositories.data.network.models

import com.google.gson.annotations.SerializedName

data class AuthorNetModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("date")
    val date: String
)