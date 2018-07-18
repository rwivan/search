package magdv.ivan.search.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import magdv.ivan.search.R
import magdv.ivan.search.data.Repository
import magdv.ivan.search.network.NetworkState
import magdv.ivan.search.ui.NetworkStateItemViewHolder
import magdv.ivan.search.ui.RepositoryViewHolder

class RepositoryAdapter(
        private val retryCallback: () -> Unit)
    : PagedListAdapter<Repository, RecyclerView.ViewHolder>(POST_COMPARATOR) {

    private var networkState: NetworkState? = null

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Repository>() {
            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                    oldItem.name == newItem.name
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item -> (holder as RepositoryViewHolder).bind(getItem(position))
            R.layout.item_network_state -> (holder as NetworkStateItemViewHolder).bindTo(networkState)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item -> RepositoryViewHolder.create(parent)
            R.layout.item_network_state -> NetworkStateItemViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}