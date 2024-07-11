package com.aco.usedoilcollection.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aco.usedoilcollection.database.entities.OilCollectionRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface OilCollectionRecordDao {
    @Insert
    suspend fun insert(record: OilCollectionRecord)

    @Query("SELECT * FROM oil_collection_records ORDER BY dateTime DESC")
    fun getAllRecords(): Flow<List<OilCollectionRecord>>

    @Query("SELECT * FROM oil_collection_records WHERE dateTime BETWEEN :startOfDay AND :endOfDay")
    fun getRecordsForToday(startOfDay: Long, endOfDay: Long): Flow<List<OilCollectionRecord>>


    @Query("SELECT name FROM users WHERE id = :userId")
    suspend fun getUserNameById(userId: Int): String?

    @Query("SELECT name FROM locations WHERE id = :locationId")
    suspend fun getLocationNameById(locationId: Int): String?
}
