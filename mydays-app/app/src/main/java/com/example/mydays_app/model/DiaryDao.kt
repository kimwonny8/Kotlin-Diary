package com.example.mydays_app.model

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE

@Dao
interface DiaryDao {
    @Query("select * from DiaryRecord order by year, month, date")
    fun selectAll() : List<DiaryRecord>

    @Query("select * from DiaryRecord where did=:did")
    fun getDiary(did:Int) : List<DiaryRecord>

    @Query("select count(*) from DiaryRecord where year=:year and month=:month and date=:date")
    fun getDiaryCnt(year:Int, month: Int, date:Int) : Int

    @Update
    suspend fun update(record: DiaryRecord)

    @Insert(onConflict = IGNORE)
    suspend fun insert(record: DiaryRecord)

    @Delete
    suspend fun delete(record: DiaryRecord)
}