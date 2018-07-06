package magdv.ivan.search.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import magdv.ivan.search.App
import magdv.ivan.search.R
import magdv.ivan.search.Screen
import org.jetbrains.anko.support.v4.withArguments
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity() {
    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router.newRootScreen(Screen.START_SCREEN)
    }

    override fun onResume() {
        super.onResume()
        val navigator: Navigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.main_container) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment {
                return when (screenKey) {
                    Screen.LIST_SCREEN -> ListFragment().withArguments(Pair("q", data))
                    Screen.CARD_SCREEN -> CardFragment().withArguments(Pair("repository", data))
                    else -> StartFragment()
                }
            }

            override fun exit() {
                finish()
            }

            override fun showSystemMessage(message: String?) = Unit
        }
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        router.backTo(Screen.LIST_SCREEN)
    }
}
