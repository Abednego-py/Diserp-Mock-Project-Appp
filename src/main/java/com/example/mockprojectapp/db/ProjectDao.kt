package com.example.mockprojectapp.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(ent: ProjectEntity)

    @Delete
    fun deleteItem(ent: ProjectEntity)

    @Query("select * from ProjectEntity")
    fun retreiveAll(): Flow<List<ProjectEntity>>

    @Query("delete from ProjectEntity")
    fun deleteAll()
}