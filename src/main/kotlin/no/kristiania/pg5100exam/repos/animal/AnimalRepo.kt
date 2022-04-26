package no.kristiania.pg5100exam.repos.animal

import no.kristiania.pg5100exam.models.animal.AnimalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface AnimalRepo: JpaRepository<AnimalEntity, Long> {

    fun findByName(name: String): AnimalEntity?

    /*@Modifying
    @Query("UPDATE animals AS a SET a.name = ?1, a.age = ?2, a.breed = ?3, a.health = ?4 WHERE a.id = ?1")
    fun updateByName(name: String, age: Int, breed: String, health: String)*/

}