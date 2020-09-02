package abdulmanov.eduard.githubrepositories.domain.models

data class Commit(
    val message: String,
    val authorName: String,
    val date: String,
    val parents: List<String>
)