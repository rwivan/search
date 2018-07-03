package magdv.ivan.search.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_error.*
import magdv.ivan.search.R
import magdv.ivan.search.Screen

class ErrorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when (arguments?.getString("code")!!) {
            Screen.ERROR_SCREEN_TYPE_EMPTY -> errorText.setText(getResources().getString(R.string.empty))
            else -> errorText.setText(getResources().getString(R.string.error))
        }
    }
}
