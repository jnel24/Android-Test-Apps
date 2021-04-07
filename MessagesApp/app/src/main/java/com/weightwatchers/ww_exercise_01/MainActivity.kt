package com.weightwatchers.ww_exercise_01

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.weightwatchers.ww_exercise_01.databinding.ActivityMainBinding

private const val FRAG_TAG = "MESSAGES_FRAG"

/**
 * Activity class for displaying the MessagesFragment (also includes Snackbar functionality)
 */
class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var messagesFrag : MessagesFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Maintain state after activity restarts
        messagesFrag = if (savedInstanceState != null) {
            supportFragmentManager.findFragmentByTag(FRAG_TAG) as MessagesFragment
        } else {
            MessagesFragment()
        }
        showMessagesFrag()
    }

    fun displaySnackbar(text: String) {
        val snackbar: Snackbar = Snackbar.make(binding.container,
                text,
                Snackbar.LENGTH_LONG)
        val view = snackbar.view
        val tv = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        tv.setTextColor(Color.WHITE)
        snackbar.show()
    }

    private fun showMessagesFrag() {
        supportFragmentManager.beginTransaction().replace(binding.container.id, messagesFrag!!, FRAG_TAG).commitNow()
    }
}