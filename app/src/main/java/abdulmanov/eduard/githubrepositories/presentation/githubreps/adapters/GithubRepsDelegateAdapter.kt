package abdulmanov.eduard.githubrepositories.presentation.githubreps.adapters

import abdulmanov.eduard.githubrepositories.R
import abdulmanov.eduard.githubrepositories.presentation.common.extensions.loadImg
import abdulmanov.eduard.githubrepositories.presentation.githubreps.models.GithubRepPresentationModel
import com.livermor.delegateadapter.delegate.KDelegateAdapter
import kotlinx.android.synthetic.main.item_github_rep.view.*

class GithubRepsDelegateAdapter(
    private val clickListener: ((GithubRepPresentationModel) -> Unit)? = null
) : KDelegateAdapter<GithubRepPresentationModel>() {

    override fun KViewHolder.onBind(item: GithubRepPresentationModel) {
        itemView.run {
            ownerAvatarImageView.loadImg(item.ownerAvatarUrl, R.color.colorPlaceholder)
            ownerLoginTextView.text = item.ownerLogin
            fullNameRepTextView.text = item.fullNameRep

            setOnClickListener { clickListener?.invoke(item) }
        }
    }

    override fun isForViewType(item: Any) = item is GithubRepPresentationModel

    override fun getLayoutId() = R.layout.item_github_rep

    override fun GithubRepPresentationModel.getItemId() = this.id

    override fun GithubRepPresentationModel.getItemContent() = this
}