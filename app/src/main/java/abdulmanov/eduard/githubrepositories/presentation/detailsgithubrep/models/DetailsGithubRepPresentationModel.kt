package abdulmanov.eduard.githubrepositories.presentation.detailsgithubrep.models

data class DetailsGithubRepPresentationModel(
    val id: Long,
    val fullNameRep: String,
    val ownerLogin: String,
    val ownerAvatarUrl: String,
    val lastCommit: CommitPresentationModel
)