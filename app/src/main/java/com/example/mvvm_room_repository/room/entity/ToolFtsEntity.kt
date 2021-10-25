package com.example.mvvm_room_repository.room.entity

import androidx.room.Entity
import androidx.room.Fts4

@Entity(tableName = "toolsFts")
@Fts4(contentEntity = ToolEntity::class)
class ToolFtsEntity(
    val name: String
)