package com.example.gearlistapp.data.repository

import com.example.gearlistapp.data.dao.CategoryDao
import com.example.gearlistapp.data.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow
/**
 * Az alkalmazas kategoria repository osztalya.
 * @property categoryDao a kategoria DAO.
 */
class CategoryRepository(private val categoryDao: CategoryDao) {
    /**
     * Visszaadja az osszes kategoriat.
     * @return az osszes kategoria.
     */
    fun getAll(): Flow<List<CategoryEntity>> = categoryDao.getAllCategories()

    /**
     * Visszaadja a kategoriat az azonosito alapjan.
     * @param id a kategoria azonositoja.
     * @return a kategoria.
     */
    fun getById(id: Int?): Flow<CategoryEntity> {
        return categoryDao.getCategoryById(id)
    }

    /**
     * Beszur egy kategoriat az adatbazisba.
     * @param category a beszurando kategoria.
     */
    suspend fun insert(category: CategoryEntity) {
        /** Id generalas automatikus marad*/
        val name = category.name
        val color = category.color
        val iconName = category.iconName
        val newCategory = CategoryEntity(name = name, color = color, iconName = iconName)
        categoryDao.insertCategory(newCategory)
    }

    /**
     * Frissiti a kategoriat az adatbazisban.
     * @param category a frissitendo kategoria.
     */
    suspend fun update(category: CategoryEntity) {
        categoryDao.updateCategory(category)
    }

    /**
     * Torli a kategoriat az adatbazisbol.
     * @param category a torlendo kategoria.
     */
    suspend fun delete(category: CategoryEntity) {
        categoryDao.deleteCategory(category)
    }

    /**
     * Torli a kategoriat az azonosito alapjan.
     * @param id a kategoria azonositoja.
     */
    suspend fun deleteById(id: Int?) {
        categoryDao.deleteCategoryById(id)
    }
}