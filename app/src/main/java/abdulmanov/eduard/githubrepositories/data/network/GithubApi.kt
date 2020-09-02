package abdulmanov.eduard.githubrepositories.data.network

import abdulmanov.eduard.githubrepositories.data.network.models.GithubRepNetModel
import abdulmanov.eduard.githubrepositories.data.network.models.DetailsCommitNetModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GithubApi {

    @GET("repositories")
    fun getGithubReps(@Query("since") since: Long?): Single<List<GithubRepNetModel>>

    @GET
    fun getCommits(@Url url: String): Single<List<DetailsCommitNetModel>>
}