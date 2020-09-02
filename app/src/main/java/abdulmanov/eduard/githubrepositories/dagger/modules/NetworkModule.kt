package abdulmanov.eduard.githubrepositories.dagger.modules

import abdulmanov.eduard.githubrepositories.data.network.GithubApi
import abdulmanov.eduard.githubrepositories.data.network.RetrofitFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGithubApi(): GithubApi {
        return RetrofitFactory.getGithubApi(PROD_BASE_URL)
    }

    companion object {
        private const val PROD_BASE_URL = "https://api.github.com/"
    }
}