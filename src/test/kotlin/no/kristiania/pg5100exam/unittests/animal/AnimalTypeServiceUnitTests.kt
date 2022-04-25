package no.kristiania.pg5100exam.unittests.animal

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.models.animal.AnimalTypeEntity
import no.kristiania.pg5100exam.repos.animal.AnimalTypeRepo
import no.kristiania.pg5100exam.services.animal.AnimalTypeService
import org.junit.jupiter.api.Test

class AnimalTypeServiceUnitTests {

    private val animalTypeRepo = mockk<AnimalTypeRepo>()
    private val animalTypeService = AnimalTypeService(animalTypeRepo)

    @Test
    fun shouldGetAllAnimalTypes() {
        val type1 = "Mammals"
        val type2 = "Birds"

        val typeEntity1 = AnimalTypeEntity(1, type1)
        val typeEntity2 = AnimalTypeEntity(2, type2)

        every { animalTypeRepo.findAll() } answers {
            mutableListOf(typeEntity1, typeEntity2)
        }

        val types = animalTypeService.getTypes()

        assert(types.size == 2)
        assert(types.first { it.id == 1.toLong() }.type == type1)
    }

}