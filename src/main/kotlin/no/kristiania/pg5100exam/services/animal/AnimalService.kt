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
        val breedEntity = animal.breed?.let { animalBreedService.getBreed(it) }

        // Breed must not be null for the saving to be completed.
        if(breedEntity != null && animal.number != null) {
            val newAnimal = AnimalEntity(
                id = null,
                number = animal.number,
                name = animal.name,
                age = animal.age,
                breedId = breedEntity.id,
                health = animal.health
            )

            val a = animalRepo.save(newAnimal)

            // Creates an animalEntity with the breedEntity, instead of breed being null.
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

    fun findById(id: Long): AnimalEntity? {
        return animalRepo.findById(id).get()
    }

    fun updateAnimal(animalInfo: AnimalInfo): AnimalEntity? {
        // Only update if the animal exists in the db.
        val existingAnimal = animalInfo.id?.let { findById(it) }

        if(existingAnimal != null) {
            val getBreed = animalBreedService.getBreed(animalInfo.breed.toString())

            val newNumber = animalInfo.number ?: existingAnimal.number
            val newName = animalInfo.name ?: existingAnimal.name
            val newAge = animalInfo.age ?: existingAnimal.age
            val newBreed = getBreed ?: existingAnimal.breed
            val newBreedId = newBreed?.id
            val newHealth = animalInfo.health ?: existingAnimal.health

            val updatedAnimal = AnimalEntity(existingAnimal.id, newNumber, newName, newAge, newBreed, newBreedId, newHealth, existingAnimal.created)

            // Will replace the current AnimalEntity with the new, since IDs match.
            return animalRepo.save(updatedAnimal)
        }

        return null
    }

    fun deleteAnimal(number: Long): String {
        val existingEntity = getAnimalByNumber(number)

        if(existingEntity != null) {
            animalRepo.deleteByNumber(number)
            return "Successful deletion."
        }

        return "Deletion not complete. Animal number does not exist."

    }

}