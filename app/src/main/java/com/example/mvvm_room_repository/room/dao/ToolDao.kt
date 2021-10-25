package com.example.mvvm_room_repository.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvm_room_repository.room.entity.UserEntity

@Dao
interface ToolDao {

    // insert, deletAll 메서드는 데이터베이스의 추가/삭제 이므로, 코루틴 스코프(또는 스레드)에서 사용해주어야 합니다.
    // 코루틴 스코프에서 사용하기 위해 suspend를 붙여주었습니다.
    /**
     * UserEntity에 데이터를 삽입.
     * onConflict = OnConflictStrategy.IGNORE 의 뜻은 같은 값이 들어왔을 때 무시
     *
     * 충돌 전략(OnConflictStrategy.???)
    ABORT : 중단 처리
    FAIL  : 실패 처리
    IGNORE : 무시
    REPLACE	: 새로운 값으로 대체
    ROLLBACK : 이전 값으로 대체
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userEntity: UserEntity)

    /**
     * 데이터 모두 삭제
     */
    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

}