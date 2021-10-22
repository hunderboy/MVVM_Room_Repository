package com.example.mvvm_room_repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * Database class는 추상클래스, Singletone으로 구현되어야 합니다.
 * 일반적으로 Database는 전체 앱에 하나의 인스턴스만 만듭니다.
 ## 본래 database의 schema가 바뀌었을 경우 version을 변경해 주어야 합니다.
 */
@Database(
    // 만약 하나의 데이터 베이스가 여러 개의 entity를 가져야 한다면,
    // arrayOf() 안에 콤마로 구분해서 entity를 넣어주면 된다.
    // entities = arrayOf(User::class, Student::class)
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // UserEntity에 접근 명세를 가진 UserDao 추상 함수 선언
    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    // RoomDatabaseCallback을 만들어 onCreate()을 override 하여
                    // 데이터베이스가 처음 생성되었을때 할 행동을 코딩할 수 있습니다.
                    .addCallback(AppDatabaseCallback(scope)) // RoomDB 생성될때 초기 데이터 입력을 위해
                    .fallbackToDestructiveMigration()   //
                    .build()

                INSTANCE = instance
                // return instance
                instance
            }
        }
    }


    /**
     * Room 의 Database 의 Callback 을 반환하는
     * inner class
     */
    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        // Room 데이터 베이스 생성
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { database ->
                // CoroutineScope 비동기를 이용하여
                scope.launch {
                    populateDatabase(database.userDao()) // 초기 데이터를 삽입한다.
                }
            }
        }

        /**
         * 초기 데이터 삽입. (DB 생성 되자마자 들어가는 데이터)
         */
        suspend fun populateDatabase(userDao: UserDao) {
            //userDao.deleteAll()
            // Add User
            userDao.insert(UserEntity("Lilly","여","1993-07-25"))
        }
    }

}