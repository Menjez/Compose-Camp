package com.example.composecamp4.api

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SpeakerViewModel(savedStateHandle: SavedStateHandle): ViewModel(){
    private val repository = SpeakerRepositoryImpl()
    private val error = MutableStateFlow<String?>(null)
    private val name = savedStateHandle.get<String>("name")

    private val mSpeaker = MutableStateFlow<SpeakerDetails?>(null)
    val speaker get() = mSpeaker

    init {
        getSpeakerDetails()
    }
    private fun getSpeakerDetails(){

        Log.i("DETAIlS","Loading list")
        viewModelScope.launch {
            try {
                val speaker = repository.getSpeakerDetails(name = name?:"")
                mSpeaker.value = speaker
                Log.i("Details", "DETAILS : $speaker ")
            }
            catch (e: Exception){
                error.value = e.message.toString()
                Log.i("Details", "ERROR : ${e.localizedMessage} ")
            }
        }
    }

}