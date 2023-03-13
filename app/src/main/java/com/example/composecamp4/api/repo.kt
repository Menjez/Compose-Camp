package com.example.composecamp4.api

interface SpeakerRepository{
    suspend fun getSpeakerDetails(name: String): SpeakerDetails
}

class SpeakerRepositoryImpl : SpeakerRepository{
    private val api = SpeakersApi.getInstance()

    override suspend fun getSpeakerDetails(name: String):SpeakerDetails {
        val speakerDetails = api.getDetails(name)
        return speakerDetails.toDomain()
    }
}