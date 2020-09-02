package abdulmanov.eduard.githubrepositories.presentation.detailsgithubrep.adapters

import abdulmanov.eduard.githubrepositories.R
import abdulmanov.eduard.githubrepositories.presentation.common.extensions.inflate
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_parent.view.*

class ParentsAdapter : RecyclerView.Adapter<ParentsAdapter.ViewHolder>() {

    private val parents = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_parent))
    }

    override fun getItemCount(): Int {
        return parents.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(parents[position])
    }

    fun swapData(data: List<String>) {
        parents.clear()
        parents.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(sha: String) {
            itemView.shaTextView.text = sha
        }
    }
}