package no.kristiania.pg5100exam.unittests.animal

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.models.animal.AnimalBreedEntity
import no.kristiania.pg5100exam.models.animal.AnimalEntity
import no.kristiania.pg5100exam.models.animal.AnimalTypeEntity
import no.kristiania.pg5100exam.repos.animal.AnimalRepo
import no.kristiania.pg5100exam.services.animal.AnimalService
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class AnimalServiceUnitTests {

    private val animalRepo = mockk<AnimalRepo>()
    private val animalService = AnimalService(animalRepo)

    @Test
    fun shouldGetAllAnimals() {

        val mammalType = AnimalTypeEntity(1, "Mammal")
        val birdType = AnimalTypeEntity(2, "Bird")

        val dogBreed = AnimalBreedEntity(1, "Dog", mammalType)
        val birdBreed = AnimalBreedEntity(2, "Bird", birdType)

        val dog = AnimalEntity(1, "Fido", 4, dogBreed, "Sporty and fine.")
        val bird = AnimalEntity(2, "Jack Sparrow", 2, birdBreed, "Always drunk.")

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

        val dogBreed = AnimalBreedEntity(1, "Dog", mammalType)

        val dogName = "Fido"

        val dog = AnimalEntity(1, dogName, 4, dogBreed, "Sporty and fine.")

        every { animalRepo.findByName(dogName) } answers {
            dog
        }

        every { animalRepo.findByName(not(dogName)) } answers {
            null
        }

        val retrievedDog = animalService.getAnimalByName(dogName)

        assert(retrievedDog?.name == dogName)
        assert(retrievedDog?.breed == dogBreed)

        val nullDog = animalService.getAnimalByName("Dog")

        assertFalse(nullDog?.name == dogName)
    }

}