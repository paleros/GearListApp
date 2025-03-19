package com.example.gearlistapp.data.dao

import androidx.room.*
import com.example.gearlistapp.data.model.Gear

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
    suspend fun insertGear(gear: Gear)

    /**
     * Az osszes felszereles lekerdezese az adatbazisbol.
     * @return az osszes felszereles.
     */
    @Query("SELECT * FROM gear_table")
    suspend fun getAllGear(): List<Gear>

    /**
     * Felszereles lekerdezese az azonosito alapjan.
     * @param id a felszereles azonositoja.
     * @return a felszereles.
     */
    @Query("SELECT * FROM gear_table WHERE id = :id")
    suspend fun getGearById(id: Int): Gear

    /**
     * Felszereles frissitese az adatbazisban.
     * @param gear a frissitendo felszereles.
     */
    @Update
    suspend fun updateGear(gear: Gear)

    /**
     * Felszereles torlese az adatbazisbol.
     * @param gear a torlendo felszereles.
     */
    @Delete
    suspend fun deleteGear(gear: Gear)

    /**
     * Felszereles torlese az azonosito alapjan.
     * @param id a felszereles azonositoja.
     */
    @Query("DELETE FROM gear_table WHERE id = :id")
    suspend fun deleteGearById(id: Int)
}