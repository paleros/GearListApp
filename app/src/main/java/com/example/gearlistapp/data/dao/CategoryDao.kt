package com.example.gearlistapp.data.dao

import androidx.room.*
import com.example.gearlistapp.data.model.Category

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
    suspend fun insertCategory(category: Category)

    /**
     * Az osszes kategoria lekerdezese az adatbazisbol.
     * @return az osszes kategoria.
     */
    @Query("SELECT * FROM category_table")
    suspend fun getAllCategories(): List<Category>

    /**
     * Kategoria lekerdezese az azonosito alapjan.
     * @param id a kategoria azonositoja.
     * @return a kategoria.
     */
    @Query("SELECT * FROM category_table WHERE id = :id")
    suspend fun getCategoryById(id: Int): Category

    /**
     * Kategoria frissitese az adatbazisban.
     * @param category a frissitendo kategoria.
     */
    @Update
    suspend fun updateCategory(category: Category)

    /**
     * Kategoria frissitese az adatbazisban.
     * @param category a frissitendo kategoria.
     */
    @Delete
    suspend fun deleteCategory(category: Category)

    /**
     * Kategoria torlese az azonosito alapjan.
     * @param id a kategoria azonositoja.
     */
    @Query("DELETE FROM category_table WHERE id = :id")
    suspend fun deleteCategoryById(id: Int)
}