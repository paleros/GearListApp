package com.example.gearlistapp.data.dao

import androidx.room.*
import com.example.gearlistapp.data.entities.LocationEntity
import kotlinx.coroutines.flow.Flow

/**
 * A helyszinhez tartozo adatbazis muveletek.
 */
@Dao
interface LocationDao {

    /**
     * Helyszin beszurasa az adatbazisba.
     * @param location a beszurando helyszin.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity)

    /**
     * Az osszes helyszin lekerdezese az adatbazisbol.
     * @return az osszes helyszin.
     */
    @Query("SELECT * FROM location_table")
    fun getAllLocations(): Flow<List<LocationEntity>>

    /**
     * Helyszin lekerdezese az azonosito alapjan.
     * @param id a helyszin azonositoja.
     * @return a helyszin.
     */
    @Query("SELECT * FROM location_table WHERE id = :id")
    fun getLocationById(id: Int): Flow<LocationEntity>

    /**
     * Helyszin frissitese az adatbazisban.
     * @param location a frissitendo helyszin.
     */
    @Update
    suspend fun updateLocation(location: LocationEntity)

    /**
     * Helyszin frissitese az adatbazisban.
     * @param location a frissitendo helyszin.
     */
    @Delete
    suspend fun deleteLocation(location: LocationEntity)

    /**
     * Helyszin torlese az azonosito alapjan.
     * @param id a helyszin azonositoja.
     */
    @Query("DELETE FROM location_table WHERE id = :id")
    suspend fun deleteLocationById(id: Int)
}