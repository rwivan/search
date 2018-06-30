package magdv.ivan.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_card.*
import magdv.ivan.search.R
import magdv.ivan.search.data.Repository
import magdv.ivan.search.mvp.CardPresenter
import magdv.ivan.search.mvp.CardView

class CardFragment : MvpAppCompatFragment(), CardView {
    @InjectPresenter
    lateinit var cardPresenter: CardPresenter;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = (arguments?.get("repository") as HashMap<String, String>)
        cardPresenter.showCard(repository.get("owner")!!, repository.get("repo")!!, repository.get("license"))
    }

    override fun showRepository(repository: Repository) {
        owner.setText(repository.owner.login)
        if (name.text.isEmpty()) name.setText(repository.name)
        description.setText(repository.description)
        language.setText(repository.language)
        stargazers.setText(repository.stargazers_count.toString())
        forks.setText(repository.forks_count.toString())
        if (license.text.isEmpty()) license.setText(repository.license?.name)
        Glide
                .with(context!!)
                .load(repository.owner.avatar_url)
                .into(avatar);
    }

    override fun setUserName(name: String) {
        owner.setText(name)
    }

    override fun setLicense(body: String) {
        license.setText(body)
    }
}
