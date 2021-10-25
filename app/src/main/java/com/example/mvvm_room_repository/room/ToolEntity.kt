package com.example.mvvm_room_repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tool_table")
data class ToolEntity(
    @PrimaryKey
    @ColumnInfo(name="name")
    val name: String
)