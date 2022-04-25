package no.kristiania.pg5100exam.repos.animal

import no.kristiania.pg5100exam.models.animal.AnimalTypeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AnimalTypeRepo: JpaRepository<AnimalTypeEntity, Long> {

    fun findByType(type: String): AnimalTypeEntity?

}