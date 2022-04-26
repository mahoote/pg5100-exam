package no.kristiania.pg5100exam.repos.animal

import no.kristiania.pg5100exam.controllers.animal.AnimalInfo
import no.kristiania.pg5100exam.models.animal.AnimalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface AnimalRepo: JpaRepository<AnimalEntity, Long> {

    fun findByNumber(number: Long): AnimalEntity?

    /*@Modifying
    @Query("UPDATE AnimalEntity AS a SET a.number = ?2, a.name = ?3, a.age = ?4, a.breed = ?5, a.health = ?6 WHERE a.id = ?1")
    fun updateById(animalInfo: AnimalInfo): AnimalEntity*/

}