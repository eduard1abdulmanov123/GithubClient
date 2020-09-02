package abdulmanov.eduard.githubrepositories.presentation.githubreps

import abdulmanov.eduard.githubrepositories.domain.interactors.GithubRepsInteractor
import abdulmanov.eduard.githubrepositories.presentation.common.base.BaseViewModel
import abdulmanov.eduard.githubrepositories.presentation.githubreps.helpers.Paginator
import abdulmanov.eduard.githubrepositories.presentation.githubreps.mappers.GithubRepsMapper
import abdulmanov.eduard.githubrepositories.presentation.githubreps.models.GithubRepPresentationModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepsViewModel @Inject constructor(
    private val githubRepsInteractor: GithubRepsInteractor,
    private val mapper:GithubRepsMapper
) : BaseViewModel() {

    private val _state = MutableLiveData<Paginator.State>()
    val state: LiveData<Paginator.State>
        get() = _state

    private val _showMessageErrorEvent = LiveEvent<Throwable>()
    val showMessageErrorEvent: LiveData<Throwable>
        get() = _showMessageErrorEvent

    private val _openDetailsGithubRepScreen = LiveEvent<Unit>()
    val openDetailsGithubRepScreen: LiveData<Unit>
        get() = _openDetailsGithubRepScreen

    private val paginator = Paginator.Store()

    private var pageDisposable: Disposable? = null

    init {
        paginator.render = { _state.value = it }
        paginator.sideEffects.subscribe {
            when (it) {
                is Paginator.SideEffect.LoadPage -> loadNewPage(it.lastId)
                is Paginator.SideEffect.ErrorEvent -> _showMessageErrorEvent.value = it.error
            }
        }.connect()

        refresh()
    }

    fun refresh() = paginator.proceed(Paginator.Action.Refresh)

    fun loadNextPage() = paginator.proceed(Paginator.Action.LoadMore)

    fun repeat() = paginator.proceed(Paginator.Action.Repeat)

    fun selectGithubRep(githubRepPresentationModel: GithubRepPresentationModel) {
        val githubRep = mapper.mapPresentationModelToGithubRep(githubRepPresentationModel)
        githubRepsInteractor.selectGithubRep(githubRep)
        _openDetailsGithubRepScreen.value = null
    }

    private fun loadNewPage(lastId: Long?) {
        pageDisposable?.dispose()
        pageDisposable = githubRepsInteractor.getGithubReps(lastId)
            .doOnError { Thread.sleep(DELAY_FOR_SMOOTHNESS) }
            .map(mapper::mapGithubRepsToPresentationModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    paginator.proceed(Paginator.Action.NewPage(it))
                },
                {
                    paginator.proceed(Paginator.Action.PageError(it))
                }
            )
        pageDisposable?.connect()
    }
}