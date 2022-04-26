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

    fun getAnimalByNumber(number: Long): AnimalEntity? {
        return animalRepo.findByNumber(number)
    }

    fun addAnimal(animal: AnimalInfo): AnimalEntity? {

        println("${animal.id}, ${animal.number}, ${animal.name}")

        val breedEntity = animal.breed?.let { animalBreedService.getBreedByBreed(it) }

        // Breed must not be null for the saving to be completed.
        if(breedEntity != null && animal.number != null) {
            val newAnimal = AnimalEntity(
                id = animal.id,
                number = animal.number,
                name = animal.name,
                age = animal.age,
                breedId = breedEntity.id,
                health = animal.health
            )

            val a = animalRepo.save(newAnimal)

            // Creates an animalEntity with the breedEntity, instead of it being null.
            return AnimalEntity(
                a.id,
                a.number,
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

    /*fun updateAnimal(animalInfo: AnimalInfo): AnimalEntity {
    }*/

}