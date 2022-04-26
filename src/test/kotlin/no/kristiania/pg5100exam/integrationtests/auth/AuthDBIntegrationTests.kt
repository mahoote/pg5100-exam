package no.kristiania.pg5100exam.integrationtests.auth

import no.kristiania.pg5100exam.services.user.AuthService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(AuthService::class)
class AuthDBIntegrationTests(@Autowired private val authService: AuthService) {

    @Test
    fun shouldGetAllAuthorities() {
        val result = authService.getAuthorities()
        assert(result.isNotEmpty())
    }

}