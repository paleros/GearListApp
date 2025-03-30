package com.example.gearlistapp.data.repository

import com.example.gearlistapp.data.dao.LocationDao
import com.example.gearlistapp.data.entities.LocationEntity
import kotlinx.coroutines.flow.Flow
/**
 * Az alkalmazas lokacioinak repository osztalya.
 * @property locationDao a lokaciokat tarolo DAO
 */
class LocationRepository(private val locationDao: LocationDao) {
    /**
     * Visszaadja az osszes lokaciot.
     * @return az osszes lokacio.
     */
    fun getAll(): Flow<List<LocationEntity>> = locationDao.getAllLocations()

    /**
     * Visszaadja a lokaciot az azonosito alapjan.
     * @param id a lokacio azonositoja.
     * @return a lokacio.
     */
    fun getById(id: Int?): Flow<LocationEntity> {
        return locationDao.getLocationById(id)
    }

    /**
     * Beszur egy lokaciot az adatbazisba.
     * @param gear a beszurando lokacio.
     */
    suspend fun insert(gear: LocationEntity) {
        locationDao.insertLocation(gear)
    }

    /**
     * Frissiti a lokaciot az adatbazisban.
     * @param gear a frissitendo lokacio.
     */
    suspend fun update(gear: LocationEntity) {
        locationDao.updateLocation(gear)
    }

    /**
     * Torli a lokaciot az adatbazisbol.
     * @param gear a torlendo lokacio.
     */
    suspend fun delete(gear: LocationEntity) {
        locationDao.deleteLocation(gear)
    }

    /**
     * Torli a lokaciot az azonosito alapjan.
     * @param id a lokacio azonositoja.
     */
    suspend fun deleteById(id: Int?) {
        locationDao.deleteLocationById(id)
    }
}