package magdv.ivan.search.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import magdv.ivan.search.R
import magdv.ivan.search.data.Repository

class RecyclerViewAdapter internal constructor(internal var result: List<Repository>) : RecyclerView.Adapter<RecyclerViewAdapter.SearchResponseViewHolder>(){

    class SearchResponseViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SearchResponseViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item, viewGroup, false)
        return SearchResponseViewHolder(v)
    }

    override fun onBindViewHolder(vh: SearchResponseViewHolder, i: Int) {
        vh.name.setText(result[i].name)
        vh.description.setText(result[i].description)
        vh.language.setText(result[i].language)
        vh.stargazers.setText(result[i].stargazers_count.toString())
        vh.forks.setText(result[i].forks_count.toString())
    }

    override fun getItemCount(): Int {
        return result.size
    }
}
