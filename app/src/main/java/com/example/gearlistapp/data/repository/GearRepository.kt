package com.example.gearlistapp.data.repository

import com.example.gearlistapp.data.dao.GearDao
import com.example.gearlistapp.data.entities.GearEntity
import kotlinx.coroutines.flow.Flow
/**
 * Az alkalmazas adatbazisbol valo adatelerelesi muveletekert felelos osztaly.
 * @param gearDao a GearEntity-khez tartozo DAO.
 */
class GearRepository(private val gearDao: GearDao) {
    /**
     * Visszaadja az osszes GearEntity-t.
     * @return az osszes GearEntity.
     */
    fun getAll(): Flow<List<GearEntity>> = gearDao.getAllGears()

    /**
     * Visszaadja a GearEntity-t az azonosito alapjan.
     * @param id a GearEntity azonositoja.
     * @return a GearEntity.
     */
    fun getById(id: Int?): Flow<GearEntity> {
        return gearDao.getGearById(id)
    }

    /**
     * Beszur egy GearEntity-t az adatbazisba.
     * @param gear a beszurando GearEntity.
     * @return a beszurt GearEntity azonositoja.
     */
    suspend fun insert(gear: GearEntity) :Int {
        /** Id generalas automatikus marad*/
        val name = gear.name
        val description = gear.description
        val categoryId = gear.categoryId
        val locationId = gear.locationId
        val inPackage = gear.inPackage
        val pieces = gear.pieces
        val parent = gear.parent
        val newGear = GearEntity(name = name, description = description, categoryId = categoryId,
            locationId = locationId, inPackage = inPackage, pieces = pieces, parent = parent)
        val id = gearDao.insertGear(newGear)
        return id.toInt()
    }

    /**
     * Frissiti a GearEntity-t az adatbazisban.
     * @param gear a frissitendo GearEntity.
     */
    suspend fun update(gear: GearEntity) {
        gearDao.updateGear(gear)
    }

    /**
     * Torli a GearEntity-t az adatbazisbol.
     * @param gear a torlendo GearEntity.
     */
    suspend fun delete(gear: GearEntity) {
        gearDao.deleteGear(gear)
    }

    /**
     * Torli a GearEntity-t az azonosito alapjan.
     * @param id a GearEntity azonositoja.
     */
    suspend fun deleteById(id: Int?) {
        gearDao.deleteGearById(id)
    }
}