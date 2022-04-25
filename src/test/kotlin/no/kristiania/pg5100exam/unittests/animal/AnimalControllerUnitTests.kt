package no.kristiania.pg5100exam.unittests.animal

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.models.animal.AnimalBreedEntity
import no.kristiania.pg5100exam.models.animal.AnimalEntity
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
import org.springframework.test.web.servlet.post

@ExtendWith(SpringExtension::class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class AnimalControllerUnitTests {

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
    private lateinit var animalService: AnimalService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetAllAnimals() {
        val mammalType = AnimalTypeEntity(1, "Mammal")
        val birdType = AnimalTypeEntity(2, "Bird")

        val dogBreed = AnimalBreedEntity(1, "Dog", mammalType)
        val birdBreed = AnimalBreedEntity(2, "Bird", birdType)

        val dog = AnimalEntity(1, "Fido", 4, dogBreed, "Sporty and fine.")
        val bird = AnimalEntity(2, "Jack Sparrow", 2, birdBreed, "Always drunk.")

        every { animalService.getAnimals() } answers {
            mutableListOf(dog, bird)
        }

        mockMvc.get("/api/shelter/all")
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { jsonPath("$") {isArray()} }
            .andReturn()
    }

    @Test
    fun shouldGetAnimalByName() {
        val mammalType = AnimalTypeEntity(1, "Mammal")

        val dogBreed = AnimalBreedEntity(1, "Dog", mammalType)

        val dogName = "Fido"
        val dog = AnimalEntity(1, dogName, 4, dogBreed, "Sporty and fine.")

        every { animalService.getAnimalByName(dogName) } answers {
            dog
        }

        mockMvc.post("/api/shelter/animal") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"name\": \"$dogName\"\n" +
                    "}"
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()
    }

}