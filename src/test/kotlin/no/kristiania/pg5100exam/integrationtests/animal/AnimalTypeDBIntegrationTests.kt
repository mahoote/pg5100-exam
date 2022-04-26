package no.kristiania.pg5100exam.integrationtests.animal

import no.kristiania.pg5100exam.services.animal.AnimalBreedService
import no.kristiania.pg5100exam.services.animal.AnimalTypeService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("test")
@Import(AnimalTypeService::class)
class AnimalTypeDBIntegrationTests(@Autowired private val animalTypeService: AnimalTypeService) {

    @Test
    fun shouldGetAllTypes() {
        val result = animalTypeService.getTypes()
        assert(result.size > 1)
    }

}