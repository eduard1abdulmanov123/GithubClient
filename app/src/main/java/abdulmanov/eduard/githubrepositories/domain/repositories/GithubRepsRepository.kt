package abdulmanov.eduard.githubrepositories.domain.repositories

import abdulmanov.eduard.githubrepositories.domain.models.DetailsGithubRep
import abdulmanov.eduard.githubrepositories.domain.models.GithubRep
import io.reactivex.Single

interface GithubRepsRepository {

    fun getGithubReps(lastId: Long?): Single<List<GithubRep>>

    fun getDetailsGithubRep(githubRep: GithubRep): Single<DetailsGithubRep>
}