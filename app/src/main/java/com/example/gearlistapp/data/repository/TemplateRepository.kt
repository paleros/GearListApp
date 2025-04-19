package com.example.gearlistapp.data.repository

import com.example.gearlistapp.data.dao.TemplateDao
import com.example.gearlistapp.data.entities.TemplateEntity
import kotlinx.coroutines.flow.Flow

/**
 * Az alkalmazas adatbazisbol valo adatelerelesi muveletekert felelos osztaly.
 * @param templateDao a TemplateEntity-khez tartozo DAO.
 */
class TemplateRepository(private val templateDao: TemplateDao) {

    /**
     * Visszaadja az osszes TemplateEntity-t.
     * @return az osszes TemplateEntity.
     */
    fun getAll(): Flow<List<TemplateEntity>> = templateDao.getAllTemplates()

    /**
     * Visszaadja a TemplateEntity-t az azonosito alapjan.
     * @param id a TemplateEntity azonositoja.
     * @return a TemplateEntity.
     */
    fun getById(id: Int?): Flow<TemplateEntity> {
        return templateDao.getTemplateById(id)
    }

    /**
     * Beszur egy TemplateEntity-t az adatbazisba.
     * @param template a beszurando TemplateEntity.
     */
    suspend fun insert(template: TemplateEntity) {
        /** Id generalas automatikus marad*/
        val title = template.title
        val description = template.description
        val duration = template.duration
        val itemList = template.itemList
        val backgroundColor = template.backgroundColor

        val newTemplate = TemplateEntity(title = title, description = description,
            duration = duration, itemList = itemList, backgroundColor = backgroundColor)
        templateDao.insertTemplate(newTemplate)
    }

    /**
     * Frissiti a TemplateEntity-t az adatbazisban.
     * @param template a frissitendo TemplateEntity.
     */
    suspend fun update(template: TemplateEntity) {
        templateDao.updateTemplate(template)
    }

    /**
     * Torli a TemplateEntity-t az adatbazisbol.
     * @param template a torlendo TemplateEntity.
     */
    suspend fun delete(template: TemplateEntity) {
        templateDao.deleteTemplate(template)
    }

    /**
     * Torli a TemplateEntity-t az azonosito alapjan.
     * @param id a TemplateEntity azonositoja.
     */
    suspend fun deleteById(id: Int?) {
        templateDao.deleteTemplateById(id)
    }
}