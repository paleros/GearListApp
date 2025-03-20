package com.example.gearlistapp.data.dao

import androidx.room.*
import com.example.gearlistapp.data.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * A felszereles kategoriajahoz tartozo adatbazis muveletek.
 */
@Dao
interface CategoryDao {

    /**
     * Kategoria beszurasa az adatbazisba.
     * @param category a beszurando kategoria.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    /**
     * Az osszes kategoria lekerdezese az adatbazisbol.
     * @return az osszes kategoria.
     */
    @Query("SELECT * FROM category_table")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    /**
     * Kategoria lekerdezese az azonosito alapjan.
     * @param id a kategoria azonositoja.
     * @return a kategoria.
     */
    @Query("SELECT * FROM category_table WHERE id = :id")
    fun getCategoryById(id: Int): Flow<CategoryEntity>

    /**
     * Kategoria frissitese az adatbazisban.
     * @param category a frissitendo kategoria.
     */
    @Update
    suspend fun updateCategory(category: CategoryEntity)

    /**
     * Kategoria frissitese az adatbazisban.
     * @param category a frissitendo kategoria.
     */
    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    /**
     * Kategoria torlese az azonosito alapjan.
     * @param id a kategoria azonositoja.
     */
    @Query("DELETE FROM category_table WHERE id = :id")
    suspend fun deleteCategoryById(id: Int)
}