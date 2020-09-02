package abdulmanov.eduard.githubrepositories.dagger.components

import abdulmanov.eduard.githubrepositories.dagger.modules.DomainModule
import abdulmanov.eduard.githubrepositories.dagger.modules.NetworkModule
import abdulmanov.eduard.githubrepositories.dagger.modules.ViewModelModule
import abdulmanov.eduard.githubrepositories.presentation.detailsgithubrep.DetailsGithubRepFragment
import abdulmanov.eduard.githubrepositories.presentation.githubreps.GithubRepsFragment
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DomainModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(githubRepsFragment: GithubRepsFragment)

    fun inject(detailsGithubRepFragment: DetailsGithubRepFragment)
}