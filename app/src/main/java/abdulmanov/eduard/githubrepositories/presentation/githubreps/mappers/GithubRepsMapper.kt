package abdulmanov.eduard.githubrepositories.presentation.githubreps.mappers

import abdulmanov.eduard.githubrepositories.domain.models.GithubRep
import abdulmanov.eduard.githubrepositories.presentation.githubreps.models.GithubRepPresentationModel
import javax.inject.Inject

class GithubRepsMapper @Inject constructor() {

    fun mapGithubRepsToPresentationModel(githubReps:List<GithubRep>): List<GithubRepPresentationModel>{
        return githubReps.map {
            GithubRepPresentationModel(
                id = it.id,
                fullNameRep = it.fullNameRep,
                ownerLogin = it.ownerLogin,
                ownerAvatarUrl = it.ownerAvatarUrl,
                commitsUrl = it.commitsUrl
            )
        }
    }

    fun mapPresentationModelToGithubRep(githubRepPresentationModel: GithubRepPresentationModel): GithubRep{
        return GithubRep(
            id = githubRepPresentationModel.id,
            fullNameRep = githubRepPresentationModel.fullNameRep,
            ownerLogin = githubRepPresentationModel.ownerLogin,
            ownerAvatarUrl = githubRepPresentationModel.ownerAvatarUrl,
            commitsUrl = githubRepPresentationModel.commitsUrl
        )
    }
}