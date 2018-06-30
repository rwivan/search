package magdv.ivan.search.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class PaginationScrollListener (internal var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val itemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= itemCount
                    && firstVisibleItemPosition >= 0
                    && itemCount < getTotalCount()) {
                loadMoreItems()
            }
        }

    }

    protected abstract fun loadMoreItems()

    abstract fun getTotalCount(): Int

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean
}
