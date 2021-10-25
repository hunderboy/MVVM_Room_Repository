package com.example.mvvm_room_repository.room.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val mApplication = application

    private val _main_text : MutableLiveData<String> = MutableLiveData("Main")
    val main_text: LiveData<String> get() = _main_text

    fun onClickButton(){
        // TODO: Click 시 Room에 데이터를 추가해야 함.
        Toast.makeText(mApplication,"Click!",Toast.LENGTH_SHORT).show()
    }

}