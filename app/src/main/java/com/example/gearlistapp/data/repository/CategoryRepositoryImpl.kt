package com.example.gearlistapp.data.repository

import com.example.gearlistapp.data.dao.CategoryDao
import com.example.gearlistapp.data.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow
/**
 * Az alkalmazas kategoria repository osztalya.
 * @property categoryDao a kategoria DAO.
 */
class CategoryRepositoryImpl(private val categoryDao: CategoryDao) : Repository<CategoryEntity> {
    /**
     * Visszaadja az osszes kategoriat.
     * @return az osszes kategoria.
     */
    override fun getAll(): Flow<List<CategoryEntity>> = categoryDao.getAllCategories()

    /**
     * Visszaadja a kategoriat az azonosito alapjan.
     * @param id a kategoria azonositoja.
     * @return a kategoria.
     */
    override fun getById(id: Int): Flow<CategoryEntity> {
        return categoryDao.getCategoryById(id)
    }

    /**
     * Beszur egy kategoriat az adatbazisba.
     * @param category a beszurando kategoria.
     */
    override suspend fun insert(category: CategoryEntity) {
        categoryDao.insertCategory(category)
    }

    /**
     * Frissiti a kategoriat az adatbazisban.
     * @param category a frissitendo kategoria.
     */
    override suspend fun update(category: CategoryEntity) {
        categoryDao.updateCategory(category)
    }

    /**
     * Torli a kategoriat az adatbazisbol.
     * @param category a torlendo kategoria.
     */
    override suspend fun delete(category: CategoryEntity) {
        categoryDao.deleteCategory(category)
    }

    /**
     * Torli a kategoriat az azonosito alapjan.
     * @param id a kategoria azonositoja.
     */
    override suspend fun deleteById(id: Int) {
        categoryDao.deleteCategoryById(id)
    }
}