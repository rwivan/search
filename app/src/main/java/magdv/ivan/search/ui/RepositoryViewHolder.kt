package magdv.ivan.search.ui

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

class RepositoryViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var cv: CardView = itemView.findViewById(R.id.cardView)
    private var name: TextView = itemView.findViewById(R.id.name)
    private var description: TextView = itemView.findViewById(R.id.description)
    private var language: TextView = itemView.findViewById(R.id.language)
    private var stargazers: TextView = itemView.findViewById(R.id.stargazers)
    private var forks: TextView = itemView.findViewById(R.id.forks)
    private var repository: Repository? = null
    @Inject
    lateinit var router: Router

    init {
        App.appComponent.inject(this)
        itemView.setOnClickListener {
            router.navigateTo(Screen.CARD_SCREEN,
                    hashMapOf(
                            "owner" to repository?.owner?.login,
                            "repo" to repository?.name,
                            "license" to repository?.license?.key
                    )
            )
        }
    }

    companion object {
        fun create(parent: ViewGroup): RepositoryViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item, parent, false)
            return RepositoryViewHolder(view)
        }
    }

    fun bind(repository: Repository?) {
        this.repository = repository
        name.setText(this.repository?.name)
        description.setText(this.repository?.description)
        language.setText(this.repository?.language)
        stargazers.setText(this.repository?.starCount.toString())
        forks.setText(this.repository?.forksCount.toString())
    }
}