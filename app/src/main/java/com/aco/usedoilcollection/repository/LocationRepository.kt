package com.aco.usedoilcollection.repository


import com.aco.usedoilcollection.database.entities.Location
import com.aco.usedoilcollection.database.dao.LocationDao
import kotlinx.coroutines.flow.Flow

class LocationRepository(private val locationDao: LocationDao) {

    suspend fun insertLocation(location: Location) {
        locationDao.insert(location)
    }

    suspend fun updateLocation(location: Location) {
        locationDao.update(location)
    }

    suspend fun deleteLocation(location: Location) {
        locationDao.delete(location)
    }

    fun getAllLocations(): Flow<List<Location>> {
        return locationDao.getAllLocations()
    }
}
