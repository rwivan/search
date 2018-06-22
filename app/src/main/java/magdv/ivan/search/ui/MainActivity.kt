package magdv.ivan.search.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import magdv.ivan.search.App
import magdv.ivan.search.R
import magdv.ivan.search.mvp.MainPresenter
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter;

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val navigator: Navigator = object: SupportFragmentNavigator(supportFragmentManager, R.id.main_container) {
            override fun createFragment(screenKey: String, data: Any): Fragment {
                return Fragment()
            }

            override fun exit() {
                finish()
            }

            override fun showSystemMessage(message: String?) {

            }
        }
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}
