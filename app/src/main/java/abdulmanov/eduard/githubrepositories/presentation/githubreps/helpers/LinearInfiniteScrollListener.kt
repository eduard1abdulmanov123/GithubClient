package abdulmanov.eduard.githubrepositories.presentation.githubreps.helpers

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class LinearInfiniteScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val visibleThreshold: Int,
    private val funcEnd: () -> Unit
) : RecyclerView.OnScrollListener() {

    var state: PaginationState = PaginationState.Allow

    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {
            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

            if (state == PaginationState.Allow && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                recyclerView.post(funcEnd)
            }
        }
    }

    sealed class PaginationState {
        object Allow : PaginationState()
        object Ban : PaginationState()
    }
}