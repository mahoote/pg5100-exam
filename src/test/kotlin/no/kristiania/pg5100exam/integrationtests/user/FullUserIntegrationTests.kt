package no.kristiania.pg5100exam.integrationtests.user

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import kotlin.random.Random.Default.nextInt

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ActiveProfiles("tests")
class FullUserIntegrationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetAllUsersIntegrationTest() {
        val loggedInUser = mockMvc.post("/api/authentication") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"username\":\"admin\"," +
                    "\n" +
                    "    \"password\":\"pirate\"\n" +
                    "}"
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val theCookie = loggedInUser.response.getCookie("access_token")

        mockMvc.get("/api/user/all") {
            theCookie?.let { cookie(it) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { jsonPath("$") {isArray()} }
            .andReturn()
    }

    @Test
    fun shouldRegisterNewUserAndAuthenticateIntegrationTest() {

        val rand = nextInt()

        val newUser = "new_user_$rand"
        val newPassword = "newUserPassword123"

        mockMvc.post("/api/user/new") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"username\":\"$newUser\"," +
                    "\n" +
                    "    \"password\":\"$newPassword\"\n" +
                    "}"
        }
            .andExpect { status { is2xxSuccessful() } }
            .andReturn()


        val loggedInUser = mockMvc.post("/api/authentication") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"username\":\"$newUser\"," +
                    "\n" +
                    "    \"password\":\"$newPassword\"\n" +
                    "}"
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val theCookie = loggedInUser.response.getCookie("access_token")

        val users = mockMvc.get("/api/user/all") {
            theCookie?.let { cookie(it) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { jsonPath("$") {isArray()} }
            .andReturn()

        assert(users.response.contentAsString.contains(newUser))
    }

}