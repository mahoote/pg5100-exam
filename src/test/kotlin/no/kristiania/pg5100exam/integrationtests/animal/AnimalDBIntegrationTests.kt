package no.kristiania.pg5100exam.integrationtests.animal

import no.kristiania.pg5100exam.controllers.animal.AnimalInfo
import no.kristiania.pg5100exam.services.animal.AnimalBreedService
import no.kristiania.pg5100exam.services.animal.AnimalService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(AnimalService::class, AnimalBreedService::class)
class AnimalDBIntegrationTests(@Autowired private val animalService: AnimalService) {

    @Test
    fun shouldGetAllAnimals() {
        val result = animalService.getAnimals()
        assert(result.isNotEmpty())
    }

    @Test
    fun shouldAddAndGetOneAnimal() {
        val animalInfo = AnimalInfo(null, 123, "Doggo", 4, "Golden Retriever", "Very cute.")

        animalService.addAnimal(animalInfo)

        val result = animalService.getAnimalByNumber(123)
        assert(result?.name == "Doggo")
        assert(result?.age == 4)
    }

    @Test
    fun shouldUpdateOneAnimal() {
        val animalInfo = AnimalInfo(null, 123, "Doggo", 4, "Golden Retriever", "Very cute.")

        animalService.addAnimal(animalInfo)

        val getResult = animalService.getAnimalByNumber(123)

        val newInfo = AnimalInfo(id = getResult?.id, name = "Fido")

        val updateResult = animalService.updateAnimal(newInfo)

        assert(updateResult?.id == getResult?.id)
        assert(updateResult?.name == "Fido")
        assert(updateResult?.number == animalInfo.number)
    }

    @Test
    fun shouldAddAndDeleteAnimal() {
        val animalInfo = AnimalInfo(null, 123, "Doggo", 4, "Golden Retriever", "Very cute.")

        val getResult = animalService.addAnimal(animalInfo)

        assert(getResult != null)

        animalService.deleteAnimal(getResult?.number!!)

        val result = animalService.getAnimalByNumber(getResult.number!!)

        assert(result == null)
    }

}