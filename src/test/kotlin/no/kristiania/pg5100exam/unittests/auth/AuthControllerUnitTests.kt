package no.kristiania.pg5100exam.unittests.auth

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.models.user.AuthorityEntity
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
class AuthControllerUnitTests {

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
    private lateinit var authService: AuthService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetAllAuthorities() {
        val admin = AuthorityEntity(title = "ADMIN")
        val user = AuthorityEntity(title = "USER")

        every { authService.getAuthorities() } answers {
            mutableListOf(admin, user)
        }

        mockMvc.get("/api/user/auth/all")
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()
    }

}