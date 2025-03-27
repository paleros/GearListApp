package com.example.gearlistapp.data.repository

import com.example.gearlistapp.data.dao.GearDao
import com.example.gearlistapp.data.entities.GearEntity
import kotlinx.coroutines.flow.Flow
/**
 * Az alkalmazas adatbazisbol valo adatelerelesi muveletekert felelos osztaly.
 * @param gearDao a GearEntity-khez tartozo DAO.
 */
class GearRepositoryImpl(private val gearDao: GearDao) : GearRepository {
    /**
     * Visszaadja az osszes GearEntity-t.
     * @return az osszes GearEntity.
     */
    override fun getAll(): Flow<List<GearEntity>> = gearDao.getAllGears()

    /**
     * Visszaadja a GearEntity-t az azonosito alapjan.
     * @param id a GearEntity azonositoja.
     * @return a GearEntity.
     */
    override fun getById(id: Int): Flow<GearEntity> {
        return gearDao.getGearById(id)
    }

    /**
     * Beszur egy GearEntity-t az adatbazisba.
     * @param gear a beszurando GearEntity.
     */
    override suspend fun insert(gear: GearEntity) {
        gearDao.insertGear(gear)
    }

    /**
     * Frissiti a GearEntity-t az adatbazisban.
     * @param gear a frissitendo GearEntity.
     */
    override suspend fun update(gear: GearEntity) {
        gearDao.updateGear(gear)
    }

    /**
     * Torli a GearEntity-t az adatbazisbol.
     * @param gear a torlendo GearEntity.
     */
    override suspend fun delete(gear: GearEntity) {
        gearDao.deleteGear(gear)
    }

    /**
     * Torli a GearEntity-t az azonosito alapjan.
     * @param id a GearEntity azonositoja.
     */
    override suspend fun deleteById(id: Int) {
        gearDao.deleteGearById(id)
    }
}