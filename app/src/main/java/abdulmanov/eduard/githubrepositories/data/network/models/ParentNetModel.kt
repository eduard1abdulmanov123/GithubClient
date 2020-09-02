package abdulmanov.eduard.githubrepositories.data.network.models

import com.google.gson.annotations.SerializedName

data class ParentNetModel(
    @SerializedName("sha")
    val sha: String
)