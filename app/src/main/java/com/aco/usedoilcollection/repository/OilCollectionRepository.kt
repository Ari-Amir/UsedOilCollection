package com.aco.usedoilcollection.repository

import android.util.Log
import com.aco.usedoilcollection.database.entities.OilCollectionRecord
import com.aco.usedoilcollection.database.dao.OilCollectionRecordDao
import kotlinx.coroutines.flow.Flow


class OilCollectionRepository(private val oilCollectionRecordDao: OilCollectionRecordDao) {

    suspend fun insertRecord(record: OilCollectionRecord) {
        Log.d("OilCollectionRepository", "Inserting record into database: $record")
        oilCollectionRecordDao.insert(record)
    }


    fun getAllRecords(): Flow<List<OilCollectionRecord>> {
        return oilCollectionRecordDao.getAllRecords()
    }

    fun getRecordsForToday(startOfDay: Long, endOfDay: Long): Flow<List<OilCollectionRecord>> {
        return oilCollectionRecordDao.getRecordsForToday(startOfDay, endOfDay)
    }

    suspend fun getUserNameById(userId: Int): String? {
        return oilCollectionRecordDao.getUserNameById(userId)
    }

    suspend fun getLocationNameById(locationId: Int): String? {
        return oilCollectionRecordDao.getLocationNameById(locationId)
    }
}

