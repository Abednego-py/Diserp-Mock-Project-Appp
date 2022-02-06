package com.example.mockprojectapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {

    @Insert
    suspend fun create(ent: ProjectEntity)

    @Delete
    fun deleteItem(ent: ProjectEntity)

    @Query("select * from ProjectEntity")
    fun retreiveAll(): Flow<List<ProjectEntity>>

    @Query("delete from ProjectEntity")
    fun deleteAll()
}