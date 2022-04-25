package no.kristiania.pg5100exam.services.animal

import no.kristiania.pg5100exam.models.animal.AnimalTypeEntity
import no.kristiania.pg5100exam.repos.animal.AnimalTypeRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AnimalTypeService(@Autowired private val animalTypeRepo: AnimalTypeRepo) {

    fun getTypes(): List<AnimalTypeEntity> {
        return animalTypeRepo.findAll()
    }

}