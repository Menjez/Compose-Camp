package com.example.composecamp4.api

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

private const val timeout = 60_000

val ktorHttpClient = HttpClient(Android){

    install(JsonFeature){
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })

        engine {
            connectTimeout = timeout
            socketTimeout = timeout
        }
    }
    install(Logging){
        logger = object : Logger{
            override fun log(message: String) {
                Log.v("Logger =>",message)
            }
        }
        level = LogLevel.ALL
    }
    install(DefaultRequest){
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
}