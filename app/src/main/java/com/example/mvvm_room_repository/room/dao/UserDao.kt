package com.example.mvvm_room_repository.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mvvm_room_repository.room.entity.UserEntity

@Dao
interface UserDao {

    /**
     * "user_table"내의 "name" 컬럼 기준으로 모든 UserEntity를 오름차순(ASC) 정렬하여 가져옵니다.
     * (*DESC: 내림차순)
     *
     * @return
     * List<UserEntity>를 LiveData로 감싸주어 변화를 감지
     */
    @Query("SELECT * from user_table ORDER BY name ASC")
    fun getAlphabetizedUsers(): LiveData<List<UserEntity>>



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



//    @Update
//    fun update(user: UserEntity);
//    @Delete
//    fun delete(user: UserEntity);
//    // id로 데이터 가져오기
//    @Query("select * from tool_table where id = :toolId")
//    suspend fun loadTool(toolId: Int): ToolEntity
//    // 특정 필드 업데이트
//    @Query("UPDATE user_table SET name = :name WHERE id = :id")
//    suspend fun updateName(id: Int, name: String)
//    // 특정 단어가 포함된 데이터 가져오기
//    @Query("SELECT * FROM hamster WHERE name LIKE :search")
//    fun loadHamsters(search: String?): Flowable<List<Hamster>>
//    // 쿼리 검색
//    @Query("SELECT tool_table.* FROM tool_table JOIN toolsFts ON (tool_table.name = toolsFts.name) WHERE toolsFts MATCH :query")
//    fun searchAllTools(query: String?): LiveData<List<ToolEntity>>
}