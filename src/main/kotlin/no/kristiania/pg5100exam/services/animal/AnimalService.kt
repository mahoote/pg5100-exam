package no.kristiania.pg5100exam.services.animal

import no.kristiania.pg5100exam.models.animal.AnimalEntity
import no.kristiania.pg5100exam.repos.animal.AnimalRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AnimalService(@Autowired private val animalRepo: AnimalRepo) {

    fun getAnimals(): List<AnimalEntity> {
        return animalRepo.findAll()
    }

}