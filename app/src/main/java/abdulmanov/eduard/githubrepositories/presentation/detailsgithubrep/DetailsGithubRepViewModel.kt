package abdulmanov.eduard.githubrepositories.presentation.detailsgithubrep

import abdulmanov.eduard.githubrepositories.domain.interactors.GithubRepsInteractor
import abdulmanov.eduard.githubrepositories.presentation.common.base.BaseViewModel
import abdulmanov.eduard.githubrepositories.presentation.detailsgithubrep.mappers.DetailsGithubRepMapper
import abdulmanov.eduard.githubrepositories.presentation.detailsgithubrep.models.DetailsGithubRepPresentationModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class DetailsGithubRepViewModel @Inject constructor(
    private val githubRepsInteractor: GithubRepsInteractor,
    private val mapper: DetailsGithubRepMapper
) : BaseViewModel() {

    private val _state = MutableLiveData<Int>()
    val state: LiveData<Int>
        get() = _state

    private val _detailsGithubRep = MutableLiveData<DetailsGithubRepPresentationModel>()
    val detailsGithubRep: LiveData<DetailsGithubRepPresentationModel>
        get() = _detailsGithubRep

    init {
        refresh()
    }

    fun refresh() {
        _state.value = VIEW_STATE_PROGRESS
        githubRepsInteractor.getDetailsSelectedGithubRep()
            .doOnError { Thread.sleep(DELAY_FOR_SMOOTHNESS) }
            .map(mapper::mapDetailsGithubRepToPresentationModel)
            .safeSubscribe(
                {
                    _state.value = VIEW_STATE_DATA
                    _detailsGithubRep.value = it
                },
                {
                    _state.value = VIEW_STATE_ERROR
                }
        )
    }

    companion object {
        const val VIEW_STATE_PROGRESS = 1
        const val VIEW_STATE_ERROR = 2
        const val VIEW_STATE_DATA = 3
    }
}