package no.kristiania.pg5100exam.unittests.animal

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.models.animal.AnimalBreedEntity
import no.kristiania.pg5100exam.models.animal.AnimalTypeEntity
import no.kristiania.pg5100exam.services.animal.AnimalBreedService
import no.kristiania.pg5100exam.services.animal.AnimalService
import no.kristiania.pg5100exam.services.animal.AnimalTypeService
import no.kristiania.pg5100exam.services.user.AuthService
import no.kristiania.pg5100exam.services.user.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@ExtendWith(SpringExtension::class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class AnimalBreedControllerUnitTests {

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun animalService() = mockk<AnimalService>()
        @Bean
        fun animalTypeService() = mockk<AnimalTypeService>()
        @Bean
        fun animalBreedService() = mockk<AnimalBreedService>()
        @Bean
        fun authService() = mockk<AuthService>()
        @Bean
        fun userService() = mockk<UserService>()
    }

    @Autowired
    private lateinit var animalBreedService: AnimalBreedService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetAllBreeds() {
        val mammalType = AnimalTypeEntity(1, "Mammal")
        val birdType = AnimalTypeEntity(2, "Bird")

        val dogBreed = AnimalBreedEntity(1, "Dog", mammalType, mammalType.id)
        val birdBreed = AnimalBreedEntity(2, "Bird", birdType, mammalType.id)

        every { animalBreedService.getBreeds() } answers {
            mutableListOf(dogBreed, birdBreed)
        }

        mockMvc.get("/api/shelter/breed/all")
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { jsonPath("$") {isArray()} }
            .andReturn()
    }

    @Test
    fun shouldGetBreed() {
        val mammalType = AnimalTypeEntity(1, "Mammal")

        val breedName = "Dog"
        val dogBreed = AnimalBreedEntity(1, breedName, mammalType, mammalType.id)

        every { animalBreedService.getBreed(breedName) } answers {
            dogBreed
        }

        mockMvc.get("/api/shelter/breed?breed=$breedName")
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()
    }

}