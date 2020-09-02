package abdulmanov.eduard.githubrepositories.dagger.modules

import abdulmanov.eduard.githubrepositories.dagger.annotations.ViewModelKey
import abdulmanov.eduard.githubrepositories.presentation.common.base.ViewModelFactory
import abdulmanov.eduard.githubrepositories.presentation.detailsgithubrep.DetailsGithubRepViewModel
import abdulmanov.eduard.githubrepositories.presentation.githubreps.GithubRepsViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GithubRepsViewModel::class)
    abstract fun bindGithubRepsViewModel(viewModel: GithubRepsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsGithubRepViewModel::class)
    abstract fun bindDetailsGithubRepViewModel(viewModel: DetailsGithubRepViewModel): ViewModel
}