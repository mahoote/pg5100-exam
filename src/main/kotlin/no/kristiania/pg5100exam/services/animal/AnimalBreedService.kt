package no.kristiania.pg5100exam.services.animal

import no.kristiania.pg5100exam.models.animal.AnimalBreedEntity
import no.kristiania.pg5100exam.repos.animal.AnimalBreedRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AnimalBreedService(@Autowired private val animalBreedRepo: AnimalBreedRepo) {

    fun getBreeds(): List<AnimalBreedEntity> {
        return animalBreedRepo.findAll()
    }

    fun getBreedByBreed(breed: String): AnimalBreedEntity? {
        return animalBreedRepo.findByBreed(breed)
    }

}