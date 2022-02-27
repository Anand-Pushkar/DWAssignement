package com.learningcurve.digitalwolfeassignement.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.learningcurve.digitalwolfeassignement.cache.entities.MyEntity

@Dao
interface MyDao {

    @Insert
    suspend fun insertMyEntity(myEntity: MyEntity): Long

    @Query("SELECT * FROM myTable WHERE title = :title")
    suspend fun getEntityByTitle(title: String): MyEntity?

    @Query("DELETE FROM myTable WHERE title = :title")
    suspend fun deleteEntity(title: String): Int

    @Query("""
            SELECT * FROM myTable
            """)
    suspend fun getAllEntities(): List<MyEntity>

    @Update
    suspend fun updateEntity(myEntity: MyEntity): Int
}