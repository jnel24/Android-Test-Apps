package com.nelson.branch_app

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nelson.branch_app.databinding.ActivityMainBinding

private const val FRAG_TAG = "TODO_FRAG"

/**
 * Activity for displaying ToDoFragment and maintaining it's state on restart
 */
class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var toDoFrag : ToDoFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Maintain state after activity restarts
        toDoFrag = if (savedInstanceState != null) {
            supportFragmentManager.findFragmentByTag(FRAG_TAG) as ToDoFragment
        } else {
            ToDoFragment()
        }
        showToDoFrag()
    }

    private fun showToDoFrag() {
        supportFragmentManager.beginTransaction().replace(binding.container.id, toDoFrag!!, FRAG_TAG).commitNow()
    }

}

/**
 * Extension function for converting Ints from DensityPixels (dp) to Pixels (px)
 */
fun Int.toPX(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
