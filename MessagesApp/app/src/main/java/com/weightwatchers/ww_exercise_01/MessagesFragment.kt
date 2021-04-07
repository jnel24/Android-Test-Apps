package com.weightwatchers.ww_exercise_01

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.weightwatchers.ww_exercise_01.databinding.FragMessagesBinding
import com.weightwatchers.ww_exercise_01.viewmodel.MessageListViewModel


/**
 * Fragment class for displaying the messages in the recycler
 */
class MessagesFragment : Fragment() {

    private val messageListViewModel = MessageListViewModel()

    private val binding : FragMessagesBinding by lazy { FragMessagesBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupErrorObserver()
        setupRecycler()
        return binding.root.rootView
    }

    private fun setupErrorObserver() {
        messageListViewModel.getError().observe(this, androidx.lifecycle.Observer {
            if (!it.isNullOrEmpty()) {
                val toast = "API Call failed. Ensure your endpoint is setup correctly."
                Toast.makeText(context, toast, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setupRecycler() {
        binding.messagesRecycler.layoutManager = LinearLayoutManager(activity)
        messageListViewModel.getMessages().observe(this, androidx.lifecycle.Observer {
            binding.messagesEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            binding.messagesRecycler.adapter = MessagesAdapter(activity as MainActivity, it)
            setupSearchBar()
        })
    }

    private fun setupSearchBar() {
        binding.messagesSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                (binding.messagesRecycler.adapter as MessagesAdapter).filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}