package com.aco.usedoilcollection.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "oil_collection_records")
data class OilCollectionRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dateTime: Long,
    val litersCollected: Int,
    val userId: Int,
    val locationId: Int,
    val locationName: String
)

