package com.weightwatchers.ww_exercise_01.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weightwatchers.ww_exercise_01.api.WWService
import com.weightwatchers.ww_exercise_01.data.MessageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

/**
 * ViewModel for downloading the latest Messages
 */
class MessageListViewModel : ViewModel() {
    private val endpoint = "https://www.weightwatchers.com/assets/cmx/us/messages/collections.json"

    private val messages: MutableLiveData<List<MessageModel>> by lazy {
        MutableLiveData<List<MessageModel>>().also {
            downloadMessages()
        }
    }

    private val error: MutableLiveData<String?> by lazy { MutableLiveData<String?>() }

    fun getMessages(): LiveData<List<MessageModel>> {
        return messages
    }

    fun getError() : LiveData<String?> {
        return error
    }

    private fun downloadMessages() {
        val call = WWService.create().getMessages(endpoint)
        call.enqueue(object: Callback<List<MessageModel>> {

            override fun onResponse(call: Call<List<MessageModel>>, response: Response<List<MessageModel>>) {
                val messageList = response.body()
                if (messageList != null) {
                    messages.postValue(messageList)
                } else Log.e("Messages Download Error", endpoint)
            }

            override fun onFailure(call: Call<List<MessageModel>>, t: Throwable) {
                error.postValue(t.toString())
                Log.e("API Call failed", t.toString())
            }

        })
    }
}