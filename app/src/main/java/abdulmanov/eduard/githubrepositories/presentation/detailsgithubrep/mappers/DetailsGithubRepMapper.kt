package abdulmanov.eduard.githubrepositories.presentation.detailsgithubrep.mappers

import abdulmanov.eduard.githubrepositories.domain.common.DateFormatter
import abdulmanov.eduard.githubrepositories.domain.models.Commit
import abdulmanov.eduard.githubrepositories.domain.models.DetailsGithubRep
import abdulmanov.eduard.githubrepositories.presentation.detailsgithubrep.models.CommitPresentationModel
import abdulmanov.eduard.githubrepositories.presentation.detailsgithubrep.models.DetailsGithubRepPresentationModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DetailsGithubRepMapper @Inject constructor() {

    fun mapDetailsGithubRepToPresentationModel(detailsGithubRep: DetailsGithubRep): DetailsGithubRepPresentationModel {
        return DetailsGithubRepPresentationModel(
            id = detailsGithubRep.id,
            fullNameRep = detailsGithubRep.fullNameRep,
            ownerLogin = detailsGithubRep.ownerLogin,
            ownerAvatarUrl = detailsGithubRep.ownerAvatarUrl,
            lastCommit = mapCommitToPresentationModel(detailsGithubRep.lastCommit)
        )
    }

    private fun mapCommitToPresentationModel(commit: Commit): CommitPresentationModel {
        return CommitPresentationModel(
            message = commit.message,
            authorName = commit.authorName,
            date = prepareDate(commit.date),
            parents = commit.parents
        )
    }

    private fun prepareDate(dateStr: String): String {
        val targetSimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return DateFormatter.convertDateFromBasicFormat(dateStr, targetSimpleDateFormat)
    }
}