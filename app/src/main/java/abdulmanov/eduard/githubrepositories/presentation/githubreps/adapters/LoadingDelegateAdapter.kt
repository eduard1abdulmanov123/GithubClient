package abdulmanov.eduard.githubrepositories.presentation.githubreps.adapters

import abdulmanov.eduard.githubrepositories.R
import abdulmanov.eduard.githubrepositories.presentation.githubreps.models.LoadingPresentationModel
import abdulmanov.eduard.githubrepositories.presentation.githubreps.models.LoadingPresentationModel.LoadingState
import android.view.View
import com.livermor.delegateadapter.delegate.KDelegateAdapter
import kotlinx.android.synthetic.main.item_loading.view.*

class LoadingDelegateAdapter(
    private val clickListener: (() -> Unit)? = null
) : KDelegateAdapter<LoadingPresentationModel>() {

    override fun KViewHolder.onBind(item: LoadingPresentationModel) {
        itemView.run {
            when (item.state) {
                is LoadingState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    messageErrorTextView.visibility = View.INVISIBLE
                    repeatTextView.visibility = View.INVISIBLE
                }
                is LoadingState.Error -> {
                    progressBar.visibility = View.INVISIBLE
                    messageErrorTextView.visibility = View.VISIBLE
                    repeatTextView.visibility = View.VISIBLE
                }
            }

            repeatTextView.setOnClickListener { clickListener?.invoke() }
        }
    }

    override fun isForViewType(item: Any) = item is LoadingPresentationModel

    override fun getLayoutId() = R.layout.item_loading

    override fun LoadingPresentationModel.getItemId() = -1

    override fun LoadingPresentationModel.getItemContent() = this
}