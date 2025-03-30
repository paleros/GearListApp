package com.example.gearlistapp.data.dao

import androidx.room.*
import com.example.gearlistapp.data.entities.GearEntity
import kotlinx.coroutines.flow.Flow

/**
 * A felszereleshez tartozo adatbazis muveletek.
 */
@Dao
interface GearDao {

    /**
     * Felszereles beszurasa az adatbazisba.
     * @param gear a beszurando felszereles.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGear(gear: GearEntity)

    /**
     * Az osszes felszereles lekerdezese az adatbazisbol.
     * @return az osszes felszereles.
     */
    @Query("SELECT * FROM gear_table")
    fun getAllGears(): Flow<List<GearEntity>>

    /**
     * Felszereles lekerdezese az azonosito alapjan.
     * @param id a felszereles azonositoja.
     * @return a felszereles.
     */
    @Query("SELECT * FROM gear_table WHERE id = :id")
    fun getGearById(id: Int?): Flow<GearEntity>

    /**
     * Felszereles frissitese az adatbazisban.
     * @param gear a frissitendo felszereles.
     */
    @Update
    suspend fun updateGear(gear: GearEntity)

    /**
     * Felszereles torlese az adatbazisbol.
     * @param gear a torlendo felszereles.
     */
    @Delete
    suspend fun deleteGear(gear: GearEntity)

    /**
     * Felszereles torlese az azonosito alapjan.
     * @param id a felszereles azonositoja.
     */
    @Query("DELETE FROM gear_table WHERE id = :id")
    suspend fun deleteGearById(id: Int?)
}