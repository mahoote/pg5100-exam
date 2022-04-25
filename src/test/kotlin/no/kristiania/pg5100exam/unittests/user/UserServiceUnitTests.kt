package no.kristiania.pg5100exam.unittests.user

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.controllers.user.UserInfo
import no.kristiania.pg5100exam.models.user.AuthorityEntity
import no.kristiania.pg5100exam.models.user.UserEntity
import no.kristiania.pg5100exam.repos.user.UserRepo
import no.kristiania.pg5100exam.services.user.AuthService
import no.kristiania.pg5100exam.services.user.UserService
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class UserServiceUnitTests {

    private val userRepo = mockk<UserRepo>()
    private val authService = mockk<AuthService>()
    private val userService = UserService(userRepo, authService)

    @Test
    fun shouldGetAllUsers() {
        val oneUsername = "one_man_1"
        val onePassword = "pirate"

        val userOne = UserEntity(username = oneUsername, password = onePassword)
        val userTwo = UserEntity(username = "second_son222", password = "pirate")

        every { userRepo.findAll() } answers {
            mutableListOf(userOne, userTwo)
        }

        val users = userService.getUsers()

        assert(users?.size == 2)
        assert(users?.first { it.username == oneUsername}?.password == onePassword)
    }

    @Test
    fun shouldRegisterNewUser() {
        val newUsername = "new_user_134"
        val newPassword = "password123"

        val newUserEntity = UserEntity(username = newUsername, password = newPassword)

        every { authService.getAuthority(any()) } answers {
            AuthorityEntity(1, "USER")
        }

        every { userRepo.save(any()) } answers {
            newUserEntity
        }

        val createdUser = userService.registerUser(UserInfo(newUsername, newPassword))

        assert(createdUser?.username == newUsername)
        assertFalse(createdUser?.password != newPassword)
    }


}