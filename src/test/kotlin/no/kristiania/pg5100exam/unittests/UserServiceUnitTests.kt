package no.kristiania.pg5100exam.unittests

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.models.user.UserEntity
import no.kristiania.pg5100exam.repos.user.UserRepo
import no.kristiania.pg5100exam.services.user.UserService
import org.junit.jupiter.api.Test

class UserServiceUnitTests {

    private val userRepo = mockk<UserRepo>()
    private val userService = UserService(userRepo)

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

}