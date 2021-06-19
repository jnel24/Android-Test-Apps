package com.nelson.testapp.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nelson.testapp.data.AppDatabase
import com.nelson.testapp.data.OfferItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).bufferedReader().use {
                    val jsonString = it.readText()
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val type = Types.newParameterizedType(List::class.java, OfferItem::class.java)
                    val offerAdapter = moshi.adapter<List<OfferItem>>(type)
                    offerAdapter.fromJson(jsonString)?.let { offers ->
                        val database = AppDatabase.getInstance(applicationContext)
                        database.offerDao().insertAll(offers)
                    }

                    Result.success()
                }
            } else {
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "DatabaseWorker"
        const val KEY_FILENAME = "DATA_FILENAME"
    }
}