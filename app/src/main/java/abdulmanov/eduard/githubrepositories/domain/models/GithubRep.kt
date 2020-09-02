package abdulmanov.eduard.githubrepositories.domain.models

data class GithubRep(
    val id: Long,
    val fullNameRep: String,
    val ownerLogin: String,
    val ownerAvatarUrl: String,
    val commitsUrl: String
)