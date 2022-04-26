package no.kristiania.pg5100exam.repos.animal

import no.kristiania.pg5100exam.controllers.animal.AnimalInfo
import no.kristiania.pg5100exam.models.animal.AnimalBreedEntity
import no.kristiania.pg5100exam.models.animal.AnimalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface AnimalRepo: JpaRepository<AnimalEntity, Long> {

    fun findByNumber(number: Long): AnimalEntity?

}