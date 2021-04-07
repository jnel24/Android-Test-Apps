package com.weightwatchers.ww_exercise_01.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.weightwatchers.ww_exercise_01.data.MessageModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Used to connect to  API to fetch Messages
 */
interface WWService {
    @GET
    fun getMessages(@Url link: String) : Call<List<MessageModel>>

    companion object {
        private const val BASE_URL = "https://www.weightwatchers.com/"
        fun create(): WWService {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
                    .create(WWService::class.java)
        }
    }
}