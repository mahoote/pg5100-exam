package no.kristiania.pg5100exam.repos.animal

import no.kristiania.pg5100exam.models.animal.AnimalBreedEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AnimalBreedRepo: JpaRepository<AnimalBreedEntity, Long> {

    fun findByBreed(breed: String): AnimalBreedEntity?

}