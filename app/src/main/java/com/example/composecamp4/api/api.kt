package com.example.composecamp4.api

import io.ktor.client.*
import io.ktor.client.request.*

class SpeakersApi(private val client: HttpClient) {
    companion object{
        fun getInstance() = SpeakersApi(client = ktorHttpClient)
    }
    private val base = "https://api.github.com/users/"

    suspend fun getDetails(name:String): SpeakerDto{
        return client.get(base.plus(name))
    }
}