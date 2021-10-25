package com.example.mvvm_room_repository.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * table 스키마 설정
 */
@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name="name")
    val name: String,

    @ColumnInfo(name="gender")
    val gender: String?,

    @ColumnInfo(name="birth")
    val birth: String?
)