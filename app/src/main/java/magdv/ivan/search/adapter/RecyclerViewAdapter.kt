package magdv.ivan.search.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import magdv.ivan.search.R
import magdv.ivan.search.network.response.SearchResponse

class RecyclerViewAdapter internal constructor(internal var searchResponse: SearchResponse) : RecyclerView.Adapter<RecyclerViewAdapter.SearchResponseViewHolder>(){

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
        vh.name.setText(searchResponse.items[i].name)
        vh.description.setText(searchResponse.items[i].description)
        vh.language.setText(searchResponse.items[i].language)
        vh.stargazers.setText(searchResponse.items[i].stargazers_count.toString())
        vh.forks.setText(searchResponse.items[i].forks_count.toString())
    }

    override fun getItemCount(): Int {
        return searchResponse.items.size
    }
}
