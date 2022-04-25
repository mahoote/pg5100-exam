package no.kristiania.pg5100exam.unittests

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.models.user.AuthorityEntity
import no.kristiania.pg5100exam.services.user.AuthService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerUnitTests {

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun authService() = mockk<AuthService>()
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

        val response = mockMvc.get("/api/authority/all") {

        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()

        val authorities = response.response.contentAsString

        assert(authorities.contains("ADMIN"))
        assert(authorities.contains("USER"))
    }

}