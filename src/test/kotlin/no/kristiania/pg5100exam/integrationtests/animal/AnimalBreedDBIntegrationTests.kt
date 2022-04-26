package no.kristiania.pg5100exam.integrationtests.animal

import no.kristiania.pg5100exam.services.animal.AnimalBreedService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(AnimalBreedService::class)
class AnimalBreedDBIntegrationTests(@Autowired private val animalBreedService: AnimalBreedService) {

    @Test
    fun shouldGetAllBreeds() {
        val result = animalBreedService.getBreeds()
        assert(result.isNotEmpty())
    }

    @Test
    fun shouldGetBreed() {
        val result = animalBreedService.getBreed("Golden Retriever")
        assert(result?.id == 1.toLong())
    }

}