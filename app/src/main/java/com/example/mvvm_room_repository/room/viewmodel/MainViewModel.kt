package com.example.mvvm_room_repository.room.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _main_text : MutableLiveData<String> = MutableLiveData()
    val rightLegDegreeLiveData: LiveData<String> get() = _main_text

}