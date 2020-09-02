package abdulmanov.eduard.githubrepositories.presentation.githubreps.models

data class LoadingPresentationModel(
    var state: LoadingState = LoadingState.Loading
) {

    sealed class LoadingState {
        object Loading : LoadingState()
        object Error : LoadingState()
    }
}