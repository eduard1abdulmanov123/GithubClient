package abdulmanov.eduard.githubrepositories.presentation.githubreps.helpers

import abdulmanov.eduard.githubrepositories.presentation.githubreps.models.GithubRepPresentationModel
import android.util.Log
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

object Paginator {

    sealed class State {
        object Empty : State()
        object EmptyProgress : State()
        data class EmptyError(val error: Throwable) : State()
        data class Data(val data: List<GithubRepPresentationModel>) : State()
        data class Refresh(val data: List<GithubRepPresentationModel>) : State()
        data class NewPageProgress(val data: List<GithubRepPresentationModel>) : State()
        data class NewPageError(val data: List<GithubRepPresentationModel>) : State()
        data class FullData(val data: List<GithubRepPresentationModel>) : State()
        data class RefreshAfterFullData(val data: List<GithubRepPresentationModel>) : State()
    }

    sealed class Action {
        object Refresh : Action()
        object Repeat : Action()
        object LoadMore : Action()
        data class NewPage(val items: List<GithubRepPresentationModel>) : Action()
        data class PageError(val error: Throwable) : Action()
    }

    sealed class SideEffect {
        data class LoadPage(val lastId: Long? = null) : SideEffect()
        data class ErrorEvent(val error: Throwable) : SideEffect()
    }

    private fun reducer(
        action: Action,
        state: State,
        sideEffectListener: (SideEffect) -> Unit
    ): State {
        return when (action) {
            is Action.Refresh -> {
                when (state) {
                    is State.Empty -> {
                        sideEffectListener.invoke(SideEffect.LoadPage(null))
                        State.EmptyProgress
                    }
                    is State.EmptyError -> {
                        sideEffectListener.invoke(SideEffect.LoadPage(null))
                        State.EmptyProgress
                    }
                    is State.Data -> {
                        sideEffectListener.invoke(SideEffect.LoadPage(null))
                        State.Refresh(state.data)
                    }
                    is State.NewPageProgress -> {
                        sideEffectListener.invoke(SideEffect.LoadPage(null))
                        State.Refresh(state.data)
                    }
                    is State.NewPageError -> {
                        sideEffectListener.invoke(SideEffect.LoadPage(null))
                        State.Refresh(state.data)
                    }
                    is State.FullData -> {
                        sideEffectListener.invoke(SideEffect.LoadPage(null))
                        State.RefreshAfterFullData(state.data)
                    }
                    else -> state
                }
            }
            is Action.Repeat -> {
                when (state) {
                    is State.NewPageError -> {
                        sideEffectListener.invoke(SideEffect.LoadPage(state.data.last().id))
                        State.NewPageProgress(state.data)
                    }
                    else -> state
                }
            }
            is Action.LoadMore -> {
                when (state) {
                    is State.Data -> {
                        sideEffectListener.invoke(SideEffect.LoadPage(state.data.last().id))
                        State.NewPageProgress(state.data)
                    }
                    is State.Refresh -> {
                        sideEffectListener.invoke(SideEffect.LoadPage(state.data.last().id))
                        State.NewPageProgress(state.data)
                    }
                    else -> state
                }
            }
            is Action.NewPage -> {
                val items = action.items
                when (state) {
                    is State.EmptyProgress -> State.Data(items)
                    is State.Refresh -> State.Data(items)
                    is State.NewPageProgress -> {
                        if (items.isEmpty()) {
                            State.FullData(state.data)
                        } else {
                            State.Data((state.data + items).distinct())
                        }
                    }
                    is State.RefreshAfterFullData -> State.Data(items)
                    else -> state
                }
            }
            is Action.PageError -> {
                when (state) {
                    is State.EmptyProgress -> State.EmptyError(action.error)
                    is State.Refresh -> {
                        sideEffectListener.invoke(SideEffect.ErrorEvent(action.error))
                        State.Data(state.data)
                    }
                    is State.NewPageProgress -> State.NewPageError(state.data)
                    is State.RefreshAfterFullData -> {
                        sideEffectListener.invoke(SideEffect.ErrorEvent(action.error))
                        State.FullData(state.data)
                    }
                    else -> state
                }
            }
        }
    }

    class Store {
        private var state: State = State.Empty
        var render: (State) -> Unit = {}

        private val sideEffectRelay = PublishRelay.create<SideEffect>()
        val sideEffects: Observable<SideEffect> = sideEffectRelay
            .hide()
            .observeOn(AndroidSchedulers.mainThread())

        fun proceed(action: Action) {
            val newState = reducer(action, state) { sideEffect ->
                sideEffectRelay.accept(sideEffect)
            }

            if (newState != state) {
                state = newState
                render.invoke(state)
            }
        }
    }
}