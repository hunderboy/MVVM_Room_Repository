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

    // 주의할것은, ViewModel에서 dialog나 새로운 activity를 띄우는 것은
    // view를 참조하는 것이기 때문에 메모리 누수가 일어납니다.
    // 이러한 활동은 activity나 fragment에서 일어나야 합니다.
    // 그러므로 버튼 onClick과 viewModel을 바인딩해놓았지만 사용하지 않기때문에 지워주겠습니다.
    // activity에서 버튼의 setOnClickListener를 사용하면 됩니다.
    //    fun onClickButton(){
    //        // TODO: Click 시 Room에 데이터를 추가해야 함.
    //        Toast.makeText(mApplication,"Click!",Toast.LENGTH_SHORT).show()
    //    }

    // 코루틴 스코프 선언
    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    private val _main_text : MutableLiveData<String> = MutableLiveData("Add")
    val main_text: LiveData<String> get() = _main_text

    val repository: Repository =  Repository(AppDatabaseVer1.getDatabase(application,viewModelScope))
    var allUsers: LiveData<List<UserEntity>> = repository.allUsers

    // 데이터 추가
    fun insert(userEntity: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(userEntity)
    }


}