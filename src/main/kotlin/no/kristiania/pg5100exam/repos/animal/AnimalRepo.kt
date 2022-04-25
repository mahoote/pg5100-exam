package no.kristiania.pg5100exam.repos.animal

import no.kristiania.pg5100exam.models.animal.AnimalEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AnimalRepo: JpaRepository<AnimalEntity, Long> {

    fun findByName(name: String): AnimalEntity?

}