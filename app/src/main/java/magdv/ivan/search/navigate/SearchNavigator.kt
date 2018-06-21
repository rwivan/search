package magdv.ivan.search.navigate

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import javax.inject.Inject

class SearchNavigator(fragmentManager: FragmentManager, containerId: Int) : SupportFragmentNavigator(fragmentManager, containerId) {
    @Inject
    lateinit var activity: Activity

    override fun createFragment(screenKey: String, data: Any): Fragment {
        return Fragment();
    }

    override fun exit() {
        activity.finish()
    }

    override fun showSystemMessage(message: String?) {

    }
}