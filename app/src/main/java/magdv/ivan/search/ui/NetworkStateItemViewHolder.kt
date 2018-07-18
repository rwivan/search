package magdv.ivan.search.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import magdv.ivan.search.R
import magdv.ivan.search.network.NetworkState
import magdv.ivan.search.network.Status

class NetworkStateItemViewHolder(view: View, private val retryCallback: () -> Unit) : RecyclerView.ViewHolder(view) {
    private val progressBar = view.findViewById<ProgressBar>(R.id.progress)
    private val retry = view.findViewById<Button>(R.id.retry_button)
    private val errorMsg = view.findViewById<TextView>(R.id.error_msg)

    init {
        retry.setOnClickListener {
            retryCallback()
        }
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_network_state, parent, false)
            return NetworkStateItemViewHolder(view, retryCallback)
        }
    }

    fun bindTo(networkState: NetworkState?) {
        progressBar.visibility = toVisbility(networkState?.status == Status.RUNNING)
        retry.visibility = toVisbility(networkState?.status == Status.FAILED)
        errorMsg.visibility = toVisbility(networkState?.msg != null)
        errorMsg.text = networkState?.msg
    }

    private fun toVisbility(constraint: Boolean): Int {
        return if (constraint) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}