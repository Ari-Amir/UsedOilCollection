package com.aco.usedoilcollection.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
