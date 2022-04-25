package no.kristiania.pg5100exam.unittests.auth

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.models.user.AuthorityEntity
import no.kristiania.pg5100exam.repos.user.AuthRepo
import no.kristiania.pg5100exam.services.user.AuthService
import org.junit.jupiter.api.Test

class AuthServiceUnitTests {

    private val authRepo = mockk<AuthRepo>()
    private val authService = AuthService(authRepo)

    @Test
    fun shouldGetAllAuthorities() {
        val auth1 = "ADMIN"
        val auth2 = "USER"

        val authEntity1 = AuthorityEntity(1, auth1)
        val authEntity2 = AuthorityEntity(2, auth2)

        every { authRepo.findAll() } answers {
            mutableListOf(authEntity1, authEntity2)
        }

        val auths = authService.getAuthorities()

        assert(auths.toString().contains(auth1))
        assert(auths.toString().contains(auth2))

        assert(auths.first { it.id == 1.toLong() }.title == auth1)
    }

}