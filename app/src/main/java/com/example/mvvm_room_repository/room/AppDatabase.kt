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
 */
@Database(
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
                    .addCallback(AppDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.userDao())
                }
            }
        }
        suspend fun populateDatabase(userDao: UserDao) {

            //userDao.deleteAll()
            // Add User
            userDao.insert(UserEntity("Lilly","여","1993-07-25"))
        }
    }

}