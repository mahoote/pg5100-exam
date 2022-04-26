package no.kristiania.pg5100exam.unittests.animal

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.models.animal.AnimalBreedEntity
import no.kristiania.pg5100exam.models.animal.AnimalEntity
import no.kristiania.pg5100exam.models.animal.AnimalTypeEntity
import no.kristiania.pg5100exam.repos.animal.AnimalBreedRepo
import no.kristiania.pg5100exam.repos.animal.AnimalTypeRepo
import no.kristiania.pg5100exam.services.animal.AnimalBreedService
import no.kristiania.pg5100exam.services.animal.AnimalTypeService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnimalBreedServiceUnitTests {

    private val animalBreedRepo = mockk<AnimalBreedRepo>()
    private val animalBreedService = AnimalBreedService(animalBreedRepo)

    @Test
    fun shouldGetAllBreeds() {
        val breed1 = "Golden Retriever"
        val breed2 = "Labrador"

        val typeEntity = AnimalTypeEntity(type = "Dog")

        val breedEntity1 = AnimalBreedEntity(1, breed1, typeEntity, typeEntity.id)
        val breedEntity2 = AnimalBreedEntity(2, breed2, typeEntity, typeEntity.id)

        every { animalBreedRepo.findAll() } answers {
            mutableListOf(breedEntity1, breedEntity2)
        }

        val breeds = animalBreedService.getBreeds()

        assert(breeds.size == 2)
        assert(breeds.first { it.id == 1.toLong() }.breed == breed1)
    }

    @Test
    fun shouldGetBreed() {
        val mammalType = AnimalTypeEntity(1, "Mammal")
        val breedName = "Dog"
        val dogBreed = AnimalBreedEntity(1, breedName, mammalType, mammalType.id)

        every { animalBreedRepo.findByBreed(breedName) } answers {
            dogBreed
        }

        val retrievedBreed = animalBreedService.getBreed(breedName)

        assert(retrievedBreed?.type == mammalType)
        assert(retrievedBreed?.typeId == mammalType.id)
    }

}