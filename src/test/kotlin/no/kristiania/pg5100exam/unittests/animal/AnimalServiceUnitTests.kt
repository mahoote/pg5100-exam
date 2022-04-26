package no.kristiania.pg5100exam.unittests.animal

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.controllers.animal.AnimalInfo
import no.kristiania.pg5100exam.models.animal.AnimalBreedEntity
import no.kristiania.pg5100exam.models.animal.AnimalEntity
import no.kristiania.pg5100exam.models.animal.AnimalTypeEntity
import no.kristiania.pg5100exam.repos.animal.AnimalRepo
import no.kristiania.pg5100exam.services.animal.AnimalBreedService
import no.kristiania.pg5100exam.services.animal.AnimalService
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class AnimalServiceUnitTests {

    private val animalRepo = mockk<AnimalRepo>()
    private val animalBreedService = mockk<AnimalBreedService>()
    private val animalService = AnimalService(animalRepo, animalBreedService)

    @Test
    fun shouldGetAllAnimals() {

        val mammalType = AnimalTypeEntity(1, "Mammal")
        val birdType = AnimalTypeEntity(2, "Bird")

        val dogBreed = AnimalBreedEntity(1, "Dog", mammalType, mammalType.id)
        val birdBreed = AnimalBreedEntity(2, "Sparrow", birdType, mammalType.id)

        val dog = AnimalEntity(1, 12345, "Fido", 4, dogBreed, dogBreed.id, "Sporty and fine.")
        val bird = AnimalEntity(2, 56789, "Jack", 2, birdBreed, birdBreed.id, "Always drunk.")

        every { animalRepo.findAll() } answers {
            mutableListOf(dog, bird)
        }

        val animals = animalService.getAnimals()

        assert(animals.size == 2)
        assert(animals.first { it.name == "Fido" }.breed == dogBreed)
        assert(animals.first { it.name == "Fido" }.age == 4)
    }

    @Test
    fun shouldGetAnimalByName() {
        val mammalType = AnimalTypeEntity(1, "Mammal")
        val dogBreed = AnimalBreedEntity(1, "Dog", mammalType, mammalType.id)
        val dogName = "Fido"
        val dogNum: Long = 12345
        val dog = AnimalEntity(1, dogNum, dogName, 4, dogBreed, dogBreed.id, "Sporty and fine.")

        every { animalRepo.findByNumber(dogNum) } answers {
            dog
        }

        every { animalRepo.findByNumber(not(dogNum)) } answers {
            null
        }

        val retrievedDog = animalService.getAnimalByNumber(dogNum)

        assert(retrievedDog?.name == dogName)
        assert(retrievedDog?.breed == dogBreed)

        val nullDog = animalService.getAnimalByNumber(0)

        assertFalse(nullDog?.name == dogName)
    }

    @Test
    fun shouldAddNewAnimal() {
        val breed = "Sparrow"
        val name = "Jack"
        val age = 4
        val health = "Always Drunk."

        val birdType = AnimalTypeEntity(1, "Bird")
        val birdBreed = AnimalBreedEntity(1, breed, birdType, birdType.id)

        val animalInfo = AnimalInfo(number = 12345, name = name, age = age, breed = breed, health = health)

        val animalEntity = AnimalEntity(1, animalInfo.number, animalInfo.name, animalInfo.age, birdBreed, birdBreed.id, animalInfo.health)

        every { animalBreedService.getBreed(breed) } answers {
            birdBreed
        }

        every { animalRepo.save(any()) } answers {
            animalEntity
        }

        val savedAnimal = animalService.addAnimal(animalInfo)

        assert(savedAnimal?.name == name)
        assert(savedAnimal?.breed == birdBreed)
    }

    @Test
    fun updateAnimalByName() {
        val birdId: Long = 1
        val breedName = "Sparrow"

        val birdType = AnimalTypeEntity(1, "Bird")
        val birdBreed = AnimalBreedEntity(1, breedName, birdType, birdType.id)

        val animalEntity = AnimalEntity(birdId, 12345, "Jack", 4, birdBreed, birdBreed.id, "Always Drunk.")

        // Get the existing entity.
        every { animalService.findById(birdId) } answers {
            animalEntity
        }
        // Find the correct breed.
        every { animalBreedService.getBreed(any()) } answers {
            birdBreed
        }
        every { animalRepo.save(any()) } answers {
            firstArg()
        }

        val newAnimalInfo = AnimalInfo(id = birdId, number = 293, name = "Tony", health = "Loves to skate.")
        val updatedEntity = animalService.updateAnimal(newAnimalInfo)

        assertFalse(updatedEntity?.number == animalEntity.number)
        assertFalse(updatedEntity?.name == animalEntity.name)
        assertFalse(updatedEntity?.health == animalEntity.health)
    }

     @Test
     fun shouldDeleteExistingAnimal() {
         val animalNum: Long = 12345
         val dog = AnimalEntity(1, animalNum, "Fido", 4, null, 1, "Sporty and fine.")

         every { animalService.getAnimalByNumber(animalNum) } answers {
             dog
         }

         every { animalRepo.deleteByNumber(animalNum) } answers {
             nothing
         }

         val response = animalService.deleteAnimal(animalNum)

         assert(response.contains("Successful"))
     }

}