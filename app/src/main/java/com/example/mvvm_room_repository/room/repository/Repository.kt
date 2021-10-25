package com.example.mvvm_room_repository.room.repository

import androidx.lifecycle.LiveData
import com.example.mvvm_room_repository.room.databases.AppDatabaseVer1
import com.example.mvvm_room_repository.room.entity.UserEntity

class Repository(mDatabase: AppDatabaseVer1) {

    private val userDao = mDatabase.userDao()
    // User list는 공공 데이터 이기 때문에 변수 allUsers는 LiveData로 초기화 됩니다.
    // 그리고 LiveData가 관찰하고 변화가 감지되면 메인 스레드에 알려줍니다.
    val allUsers: LiveData<List<UserEntity>> = userDao.getAlphabetizedUsers()

    companion object{
        private var sInstance: Repository? = null
        fun getInstance(database: AppDatabaseVer1): Repository {
            return sInstance
                ?: synchronized(this){
                    val instance = Repository(database)
                    sInstance = instance
                    instance
                }
        }
    }

    suspend fun insert(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }
}