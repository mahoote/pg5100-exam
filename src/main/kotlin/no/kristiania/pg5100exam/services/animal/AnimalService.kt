package no.kristiania.pg5100exam.services.animal

import no.kristiania.pg5100exam.controllers.animal.AnimalInfo
import no.kristiania.pg5100exam.models.animal.AnimalEntity
import no.kristiania.pg5100exam.repos.animal.AnimalRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AnimalService(
    @Autowired private val animalRepo: AnimalRepo,
    @Autowired private val animalBreedService: AnimalBreedService
) {

    fun getAnimals(): List<AnimalEntity> {
        return animalRepo.findAll()
    }

    fun getAnimalByName(name: String): AnimalEntity? {
        return animalRepo.findByName(name)
    }

    fun addAnimal(animal: AnimalInfo): AnimalEntity? {
        val breedEntity = animalBreedService.getBreed(animal.breed)

        // Breed must not be null for the saving to be completed.
        if(breedEntity != null) {
            val newAnimal = AnimalEntity(
                id = animal.id,
                name = animal.name,
                age = animal.age,
                breedId = breedEntity.id,
                health = animal.health
            )

            val a = animalRepo.save(newAnimal)

            // Creates an animalEntity with the breedEntity, instead of it being null.
            return AnimalEntity(
                a.id,
                a.name,
                a.age,
                breedEntity,
                a.breedId,
                a.health,
                a.created
            )
        }

        return null
    }

}