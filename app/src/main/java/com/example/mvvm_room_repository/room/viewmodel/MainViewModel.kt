package com.example.mvvm_room_repository.room.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm_room_repository.room.databases.AppDatabaseVer1
import com.example.mvvm_room_repository.room.entity.UserEntity
import com.example.mvvm_room_repository.room.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val mApplication = application

    private val _main_text : MutableLiveData<String> = MutableLiveData("Main")
    val main_text: LiveData<String> get() = _main_text

    fun onClickButton(){
        // TODO: Click 시 Room에 데이터를 추가해야 함.
        Toast.makeText(mApplication,"Click!",Toast.LENGTH_SHORT).show()
    }

    // 코루틴 스코프 선언
    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    val repository: Repository =  Repository(AppDatabaseVer1.getDatabase(application,viewModelScope))
    var allUsers: LiveData<List<UserEntity>> = repository.allUsers

    fun insert(userEntity: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(userEntity)
    }


}