package abdulmanov.eduard.githubrepositories.presentation.detailsgithubrep.models

data class CommitPresentationModel(
    val message: String,
    val authorName: String,
    val date: String,
    val parents: List<String>
)