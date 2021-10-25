package com.example.mvvm_room_repository.room.databases

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mvvm_room_repository.room.entity.ToolEntity
import com.example.mvvm_room_repository.room.entity.ToolFtsEntity
import com.example.mvvm_room_repository.room.entity.UserEntity
import com.example.mvvm_room_repository.room.dao.ToolDao
import com.example.mvvm_room_repository.room.dao.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [UserEntity::class, ToolEntity::class, ToolFtsEntity::class],
    version = 8,
    exportSchema = false
)
abstract class AppDatabaseVer2 : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun toolDao(): ToolDao

    private val mIsDatabaseCreated = MutableLiveData<Boolean>()

    companion object {
        @Volatile
        private var INSTANCE: AppDatabaseVer2? = null
        private const val DATABASE_NAME = "app_database"
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabaseVer2 {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabaseVer2::class.java,
                    DATABASE_NAME
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                INSTANCE?.updateDatabaseCreated(context.applicationContext)
                // return instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.toolDao())
                }
            }
        }
        suspend fun populateDatabase(toolDao: ToolDao) {
//            toolDao.deleteAll()
//            val products: List<ToolEntity> = DataGenerator.generateTools()
//            for(p in products){
//                toolDao.insert(p)
//            }
        }
    }

    /**
     * Check whether the database already exists and expose it via [.getDatabaseCreated]
     */
    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated()
        }
    }

    private fun setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true)
    }

    open fun getDatabaseCreated(): LiveData<Boolean> {
        return mIsDatabaseCreated
    }


}