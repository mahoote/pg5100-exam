package no.kristiania.pg5100exam.unittests.user

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.models.user.AuthorityEntity
import no.kristiania.pg5100exam.models.user.UserEntity
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
class UserControllerUnitTests {

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
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetAllUsers() {
        val userOne = UserEntity(username = "one_man_1", password = "pirate")
        val userTwo = UserEntity(username = "second_son222", password = "pirate")

        every { userService.getUsers() } answers {
            mutableListOf(userOne, userTwo)
        }

        mockMvc.get("/api/user/all")
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()
    }

    @Test
    fun shouldRegisterUser() {
        val newUserEntity = UserEntity(username = "new_user_134", password = "password123", authorities = mutableListOf(AuthorityEntity(1, "USER")))

        every { userService.registerUser(any()) } answers {
            newUserEntity
        }

        mockMvc.post("/api/user/new") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"username\":\"new_user_134\",\n" +
                    "    \"password\":\"password123\"\n" +
                    "}"
        }
            .andExpect { status { is2xxSuccessful() } }
            .andReturn()
    }

}