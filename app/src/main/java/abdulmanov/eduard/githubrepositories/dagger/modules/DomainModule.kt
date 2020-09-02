package abdulmanov.eduard.githubrepositories.dagger.modules

import abdulmanov.eduard.githubrepositories.data.network.GithubApi
import abdulmanov.eduard.githubrepositories.data.repositories.GithubRepsRepositoryImpl
import abdulmanov.eduard.githubrepositories.domain.interactors.GithubRepsInteractor
import abdulmanov.eduard.githubrepositories.domain.repositories.GithubRepsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideGithubRepsRepository(githubApi: GithubApi): GithubRepsRepository {
        return GithubRepsRepositoryImpl(githubApi)
    }

    @Singleton
    @Provides
    fun provideGithubRepsInteractor(repository: GithubRepsRepository): GithubRepsInteractor {
        return GithubRepsInteractor(repository)
    }
}