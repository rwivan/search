package magdv.ivan.search.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import magdv.ivan.search.App
import magdv.ivan.search.R
import magdv.ivan.search.Screen
import magdv.ivan.search.data.Repository
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class RecyclerViewAdapter internal constructor(internal var result: MutableList<Repository>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM = 0
    private val LOADING = 1
    @Inject
    lateinit var router: Router
    var isLoadingAdded: Boolean = false

    init {
        App.appComponent.inject(this)
    }

    protected class SearchResponseViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var cv: CardView
        internal var name: TextView
        internal var description: TextView
        internal var language: TextView
        internal var stargazers: TextView
        internal var forks: TextView


        init {
            cv = itemView.findViewById<View>(R.id.cardView) as CardView
            name = itemView.findViewById<View>(R.id.name) as TextView
            description = itemView.findViewById<View>(R.id.description) as TextView
            language = itemView.findViewById<View>(R.id.language) as TextView
            stargazers = itemView.findViewById<View>(R.id.stargazers) as TextView
            forks = itemView.findViewById<View>(R.id.forks) as TextView
        }
    }

    protected class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        when (i) {
            ITEM -> return SearchResponseViewHolder(inflater.inflate(R.layout.item, viewGroup, false))
            else -> return ProgressViewHolder(inflater.inflate(R.layout.progress, viewGroup, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == result.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, i: Int) {
        if (vh is SearchResponseViewHolder) {
            vh.name.setText(result[i].name)
            vh.description.setText(result[i].description)
            vh.language.setText(result[i].language)
            vh.stargazers.setText(result[i].stargazers_count.toString())
            vh.forks.setText(result[i].forks_count.toString())
            vh.cv.setOnClickListener(View.OnClickListener {
                router.navigateTo(Screen.CARD_SCREEN,
                        hashMapOf(
                                "owner" to result[i].owner.login,
                                "repo" to result[i].name,
                                "license" to result[i].license?.key
                        )
                )
            })

        }
    }

    override fun getItemCount(): Int {
        return result.size
    }

    fun addAll(list: List<Repository>) {
        result.addAll(list)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        if (result.size > 0) {
            result.add(result.last())
            notifyItemRemoved(result.size)
        }
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        if (result.size > 0) {
            result.removeAt(result.size - 1)
            notifyItemRemoved(result.size)
        }
    }
}
