package abdulmanov.eduard.githubrepositories.data.repositories

import abdulmanov.eduard.githubrepositories.data.network.GithubApi
import abdulmanov.eduard.githubrepositories.data.network.models.DetailsCommitNetModel
import abdulmanov.eduard.githubrepositories.domain.common.DateFormatter
import abdulmanov.eduard.githubrepositories.domain.models.Commit
import abdulmanov.eduard.githubrepositories.domain.models.DetailsGithubRep
import abdulmanov.eduard.githubrepositories.domain.models.GithubRep
import abdulmanov.eduard.githubrepositories.domain.repositories.GithubRepsRepository
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.*

class GithubRepsRepositoryImpl(private val githubApi: GithubApi) : GithubRepsRepository {

    override fun getGithubReps(lastId: Long?): Single<List<GithubRep>> {
        return githubApi.getGithubReps(lastId).map {
            it.map { githubRepNetModel ->
                GithubRep(
                    id = githubRepNetModel.id,
                    fullNameRep = githubRepNetModel.fullName,
                    ownerLogin = githubRepNetModel.owner.login,
                    ownerAvatarUrl = githubRepNetModel.owner.avatarUrl,
                    commitsUrl = githubRepNetModel.commitsUrl.substringBefore("{/sha}")
                )
            }
        }
    }

    override fun getDetailsGithubRep(githubRep: GithubRep): Single<DetailsGithubRep> {
        return githubApi.getCommits(githubRep.commitsUrl).map {
            DetailsGithubRep(
                id = githubRep.id,
                fullNameRep = githubRep.fullNameRep,
                ownerLogin = githubRep.ownerLogin,
                ownerAvatarUrl = githubRep.ownerAvatarUrl,
                lastCommit = getLastCommit(it)
            )
        }
    }

    private fun getLastCommit(commits: List<DetailsCommitNetModel>): Commit {
        val lastCommit = commits.first()
        return Commit(
            message = lastCommit.commit.message,
            authorName = lastCommit.commit.author.name,
            date = getDate(lastCommit.commit.author.date),
            parents = lastCommit.parents.map { it.sha }
        )
    }

    private fun getDate(dateStr: String): String {
        val originalDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        return DateFormatter.convertDateToBasicFormat(dateStr, originalDateFormat)
    }
}