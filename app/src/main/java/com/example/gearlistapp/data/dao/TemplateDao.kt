package com.example.gearlistapp.data.dao

import androidx.room.*
import com.example.gearlistapp.data.entities.TemplateEntity
import kotlinx.coroutines.flow.Flow

/**
 * A sablonhoz tartozo adatbazis muveletek.
 */
@Dao
interface TemplateDao {

    /**
     * Sablon beszurasa az adatbazisba.
     * @param template a beszurando sablon.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(template: TemplateEntity)

    /**
     * Az osszes sablon lekerdezese az adatbazisbol.
     * @return az osszes sablon.
     */
    @Query("SELECT * FROM template_table")
    fun getAllTemplates(): Flow<List<TemplateEntity>>

    /**
     * Sablon lekerdezese az azonosito alapjan.
     * @param id a sablon azonositoja.
     * @return a sablon.
     */
    @Query("SELECT * FROM template_table WHERE id = :id")
    fun getTemplateById(id: Int?): Flow<TemplateEntity>

    /**
     * Sablon frissitese az adatbazisban.
     * @param template a frissitendo sablon.
     */
    @Update
    suspend fun updateTemplate(template: TemplateEntity)

    /**
     * Sablon torlese az adatbazisbol.
     * @param template a torlendo sablon.
     */
    @Delete
    suspend fun deleteTemplate(template: TemplateEntity)

    /**
     * Sablon torlese az azonosito alapjan.
     * @param id a sablon azonositoja.
     */
    @Query("DELETE FROM template_table WHERE id = :id")
    suspend fun deleteTemplateById(id: Int?)
}