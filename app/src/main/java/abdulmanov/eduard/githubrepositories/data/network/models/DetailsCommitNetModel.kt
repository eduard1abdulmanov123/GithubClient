package abdulmanov.eduard.githubrepositories.data.network.models

import com.google.gson.annotations.SerializedName

data class DetailsCommitNetModel(
    @SerializedName("commit")
    val commit: CommitNetModel,
    @SerializedName("parents")
    val parents: List<ParentNetModel>
)