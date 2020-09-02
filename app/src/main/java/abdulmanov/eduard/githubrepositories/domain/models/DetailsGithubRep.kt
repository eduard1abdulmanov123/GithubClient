package abdulmanov.eduard.githubrepositories.domain.models

data class DetailsGithubRep(
    val id: Long,
    val fullNameRep: String,
    val ownerLogin: String,
    val ownerAvatarUrl: String,
    val lastCommit: Commit
)