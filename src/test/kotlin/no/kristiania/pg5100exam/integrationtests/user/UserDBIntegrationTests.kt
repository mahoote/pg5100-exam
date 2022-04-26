package no.kristiania.pg5100exam.integrationtests.user

import no.kristiania.pg5100exam.controllers.user.UserInfo
import no.kristiania.pg5100exam.services.user.AuthService
import no.kristiania.pg5100exam.services.user.UserService
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("test")
@Import(UserService::class, AuthService::class)
class UserDBIntegrationTests(@Autowired private val userService: UserService) {

    @Test
    fun shouldGetAllUsers() {
        val result = userService.getUsers()

        assert(result.size > 1)
    }

    @Test
    fun shouldRegisterUser() {
        val userInfo = UserInfo("user1", "secret_password")
        val result = userService.registerUser(userInfo)

        assert(result?.username == userInfo.username)
        assertFalse(result?.password == userInfo.password)
    }

}