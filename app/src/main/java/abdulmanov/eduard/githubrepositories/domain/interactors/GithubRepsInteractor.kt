package abdulmanov.eduard.githubrepositories.domain.interactors

import abdulmanov.eduard.githubrepositories.domain.models.DetailsGithubRep
import abdulmanov.eduard.githubrepositories.domain.models.GithubRep
import abdulmanov.eduard.githubrepositories.domain.repositories.GithubRepsRepository
import io.reactivex.Single
import java.lang.Exception

class GithubRepsInteractor(private val githubRepsRepository: GithubRepsRepository) {

    private var selectedGithubRep: GithubRep? = null

    fun getGithubReps(lastId: Long? = null): Single<List<GithubRep>> {
        return githubRepsRepository.getGithubReps(lastId)
    }

    fun selectGithubRep(githubRep: GithubRep) {
        selectedGithubRep = githubRep
    }

    fun getDetailsSelectedGithubRep(): Single<DetailsGithubRep> {
        selectedGithubRep ?: throw Exception("The selected githubRep is missing")

        return githubRepsRepository.getDetailsGithubRep(selectedGithubRep!!)
    }
}