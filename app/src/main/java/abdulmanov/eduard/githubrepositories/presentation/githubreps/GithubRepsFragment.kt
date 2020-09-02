package abdulmanov.eduard.githubrepositories.presentation.githubreps

import android.os.Bundle
import androidx.fragment.app.Fragment
import abdulmanov.eduard.githubrepositories.R
import abdulmanov.eduard.githubrepositories.presentation.App
import abdulmanov.eduard.githubrepositories.presentation.common.base.ViewModelFactory
import abdulmanov.eduard.githubrepositories.presentation.detailsgithubrep.DetailsGithubRepFragment
import abdulmanov.eduard.githubrepositories.presentation.githubreps.adapters.GithubRepsDelegateAdapter
import abdulmanov.eduard.githubrepositories.presentation.githubreps.adapters.LoadingDelegateAdapter
import abdulmanov.eduard.githubrepositories.presentation.githubreps.helpers.LinearInfiniteScrollListener
import abdulmanov.eduard.githubrepositories.presentation.githubreps.helpers.Paginator
import abdulmanov.eduard.githubrepositories.presentation.githubreps.models.LoadingPresentationModel
import abdulmanov.eduard.githubrepositories.presentation.navigation.Navigation
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import kotlinx.android.synthetic.main.fragment_github_reps.*
import kotlinx.android.synthetic.main.layout_network_error.view.*
import javax.inject.Inject

class GithubRepsFragment : Fragment(R.layout.fragment_github_reps) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<GithubRepsViewModel> { viewModelFactory }

    private lateinit var adapter: CompositeDelegateAdapter
    private lateinit var scrollListener: LinearInfiniteScrollListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        viewModel.state.observe(viewLifecycleOwner, Observer(this::setState))
        viewModel.showMessageErrorEvent.observe(viewLifecycleOwner, Observer { showMessageError() })
        viewModel.openDetailsGithubRepScreen.observe(viewLifecycleOwner, Observer { openDetailsGithubScreen() })
    }

    private fun initUI() {
        toolbar.setTitle(R.string.github_reps_title)

        val layoutManager = LinearLayoutManager(requireContext())
        scrollListener = LinearInfiniteScrollListener(layoutManager, 1, viewModel::loadNextPage)
        adapter = CompositeDelegateAdapter(
            GithubRepsDelegateAdapter(viewModel::selectGithubRep),
            LoadingDelegateAdapter(viewModel::repeat)
        )

        githubRepsRecyclerView.hasFixedSize()
        githubRepsRecyclerView.addOnScrollListener(scrollListener)
        githubRepsRecyclerView.layoutManager = layoutManager
        githubRepsRecyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

        layoutNetworkError.repeatTextView.setOnClickListener {
            viewModel.refresh()
        }
    }

    private fun setState(state: Paginator.State) {
        when (state) {
            is Paginator.State.Empty -> {
                scrollListener.state = LinearInfiniteScrollListener.PaginationState.Ban
                swipeRefreshLayout.isRefreshing = false
                swipeRefreshLayout.visibility = View.GONE
                progressBar.visibility = View.GONE
                layoutNetworkError.visibility = View.GONE
            }
            is Paginator.State.EmptyProgress -> {
                scrollListener.state = LinearInfiniteScrollListener.PaginationState.Ban
                swipeRefreshLayout.isRefreshing = false
                swipeRefreshLayout.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                layoutNetworkError.visibility = View.GONE
            }
            is Paginator.State.EmptyError -> {
                scrollListener.state = LinearInfiniteScrollListener.PaginationState.Ban
                swipeRefreshLayout.isRefreshing = false
                swipeRefreshLayout.visibility = View.GONE
                progressBar.visibility = View.GONE
                layoutNetworkError.visibility = View.VISIBLE
            }
            is Paginator.State.Data -> {
                scrollListener.state = LinearInfiniteScrollListener.PaginationState.Allow
                swipeRefreshLayout.isRefreshing = false
                swipeRefreshLayout.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                layoutNetworkError.visibility = View.GONE
                adapter.swapData(state.data)
            }
            is Paginator.State.Refresh -> {
                scrollListener.state = LinearInfiniteScrollListener.PaginationState.Allow
                swipeRefreshLayout.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                layoutNetworkError.visibility = View.GONE
                adapter.swapData(state.data)
            }
            is Paginator.State.NewPageProgress -> {
                scrollListener.state = LinearInfiniteScrollListener.PaginationState.Ban
                swipeRefreshLayout.isRefreshing = false
                swipeRefreshLayout.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                layoutNetworkError.visibility = View.GONE
                adapter.swapData(state.data + LoadingPresentationModel())
            }
            is Paginator.State.NewPageError -> {
                scrollListener.state = LinearInfiniteScrollListener.PaginationState.Ban
                swipeRefreshLayout.isRefreshing = false
                swipeRefreshLayout.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                layoutNetworkError.visibility = View.GONE
                adapter.swapData(state.data + LoadingPresentationModel(LoadingPresentationModel.LoadingState.Error))
            }
            is Paginator.State.FullData -> {
                scrollListener.state = LinearInfiniteScrollListener.PaginationState.Ban
                swipeRefreshLayout.isRefreshing = false
                swipeRefreshLayout.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                layoutNetworkError.visibility = View.GONE
                adapter.swapData(state.data)
            }
            is Paginator.State.RefreshAfterFullData -> {
                scrollListener.state = LinearInfiniteScrollListener.PaginationState.Ban
                swipeRefreshLayout.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                layoutNetworkError.visibility = View.GONE
                adapter.swapData(state.data)
            }
        }
    }

    private fun showMessageError() {
        Toast.makeText(requireContext(), R.string.short_network_error, Toast.LENGTH_SHORT).show()
    }

    private fun openDetailsGithubScreen() {
        val fragment = DetailsGithubRepFragment.newInstance()
        (requireActivity() as Navigation).forwardFragment(fragment)
    }

    companion object {
        fun newInstance(): GithubRepsFragment {
            return GithubRepsFragment()
        }
    }
}