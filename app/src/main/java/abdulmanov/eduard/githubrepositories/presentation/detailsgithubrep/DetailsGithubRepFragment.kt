package abdulmanov.eduard.githubrepositories.presentation.detailsgithubrep

import androidx.fragment.app.Fragment
import abdulmanov.eduard.githubrepositories.R
import abdulmanov.eduard.githubrepositories.presentation.App
import abdulmanov.eduard.githubrepositories.presentation.common.base.ViewModelFactory
import abdulmanov.eduard.githubrepositories.presentation.common.extensions.loadImg
import abdulmanov.eduard.githubrepositories.presentation.detailsgithubrep.adapters.ParentsAdapter
import abdulmanov.eduard.githubrepositories.presentation.detailsgithubrep.models.DetailsGithubRepPresentationModel
import abdulmanov.eduard.githubrepositories.presentation.navigation.Navigation
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_details_github_rep.*
import kotlinx.android.synthetic.main.item_loading.view.*
import javax.inject.Inject

class DetailsGithubRepFragment : Fragment(R.layout.fragment_details_github_rep) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<DetailsGithubRepViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exit()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        viewModel.state.observe(viewLifecycleOwner, Observer(this::setState))
        viewModel.detailsGithubRep.observe(viewLifecycleOwner, Observer(this::setData))
    }

    private fun initUI() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { exit() }

        parentsRecyclerView.setHasFixedSize(true)
        parentsRecyclerView.layoutManager = LinearLayoutManager(context)
        parentsRecyclerView.adapter = ParentsAdapter()

        layoutNetworkError.repeatTextView.setOnClickListener {
            viewModel.refresh()
        }
    }

    private fun setState(state: Int) {
        when (state) {
            DetailsGithubRepViewModel.VIEW_STATE_PROGRESS -> {
                progressBar.visibility = View.VISIBLE
                layoutNetworkError.visibility = View.GONE
                contentConstraintLayout.visibility = View.GONE
            }
            DetailsGithubRepViewModel.VIEW_STATE_ERROR -> {
                progressBar.visibility = View.GONE
                layoutNetworkError.visibility = View.VISIBLE
                contentConstraintLayout.visibility = View.GONE
            }
            DetailsGithubRepViewModel.VIEW_STATE_DATA -> {
                progressBar.visibility = View.GONE
                layoutNetworkError.visibility = View.GONE
                contentConstraintLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun setData(detailsGithubRep: DetailsGithubRepPresentationModel) {
        ownerAvatarImageView.loadImg(detailsGithubRep.ownerAvatarUrl, R.color.colorPlaceholder)
        ownerLoginTextView.text = detailsGithubRep.ownerLogin
        fullNameRepTextView.text = detailsGithubRep.fullNameRep
        commitAuthorNameTextView.text = detailsGithubRep.lastCommit.authorName
        commitDateTextView.text = detailsGithubRep.lastCommit.date
        commitMessageTextView.text = detailsGithubRep.lastCommit.message
        (parentsRecyclerView.adapter as ParentsAdapter).swapData(detailsGithubRep.lastCommit.parents)
    }

    private fun exit() {
        (requireActivity() as Navigation).fragmentBack()
    }

    companion object {
        fun newInstance(): DetailsGithubRepFragment {
            return DetailsGithubRepFragment()
        }
    }
}