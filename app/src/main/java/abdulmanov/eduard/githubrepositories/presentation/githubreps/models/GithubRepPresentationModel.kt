package abdulmanov.eduard.githubrepositories.presentation.githubreps.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubRepPresentationModel(
    val id: Long,
    val fullNameRep: String,
    val ownerLogin: String,
    val ownerAvatarUrl: String,
    val commitsUrl: String
) : Parcelable