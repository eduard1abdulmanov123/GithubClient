package abdulmanov.eduard.githubrepositories.data.network.models

import com.google.gson.annotations.SerializedName

data class CommitNetModel(
    @SerializedName("author")
    val author: AuthorNetModel,
    @SerializedName("message")
    val message: String
)