package abdulmanov.eduard.githubrepositories.data.network.models

import com.google.gson.annotations.SerializedName

data class GithubRepNetModel(
    @SerializedName("id")
    val id: Long,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("owner")
    val owner: OwnerNetModel,
    @SerializedName("commits_url")
    val commitsUrl: String
)