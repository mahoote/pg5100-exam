package no.kristiania.pg5100exam.integrationtests.animal

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.*
import kotlin.random.Random.Default.nextInt

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FullAnimalIntegrationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetAllAnimalsIntegrationTest() {
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

        mockMvc.get("/api/shelter/all") {
            theCookie?.let { cookie(it) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { jsonPath("$") {isArray()} }
            .andReturn()
    }

    @Test
    fun shouldGetOneAnimalIntegrationTest() {
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

        mockMvc.get("/api/shelter/animal?number=12345") {
            theCookie?.let { cookie(it) }
        }
            .andExpect { status { isOk() } }
            .andReturn()
    }

    @Test
    fun shouldAddAnimalIntegrationTest() {
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

        val newNum = nextInt()

        mockMvc.post("/api/shelter/new") {
            theCookie?.let { cookie(it) }
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"number\": $newNum,\n" +
                    "    \"name\": \"Fido 2\",\n" +
                    "    \"age\": 4,\n" +
                    "    \"breed\": \"Golden Retriever\",\n" +
                    "    \"health\": \"Great dog!\"\n" +
                    "}"
        }
            .andExpect { status { is2xxSuccessful() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()

        val animals = mockMvc.get("/api/shelter/all") {
            theCookie?.let { cookie(it) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { jsonPath("$") {isArray()} }
            .andReturn()

        assert(animals.response.contentAsString.contains("$newNum"))
    }

    @Test
    fun shouldUpdateAnimalIntegrationTest() {
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

        val newNum = nextInt()

        mockMvc.put("/api/shelter/update") {
            theCookie?.let { cookie(it) }
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"id\": 1,\n" +
                    "    \"number\": $newNum,\n" +
                    "    \"name\": \"Another dog\"\n" +
                    "}"
        }
            .andExpect { status { is2xxSuccessful() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()

        val animals = mockMvc.get("/api/shelter/all") {
            theCookie?.let { cookie(it) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { jsonPath("$") {isArray()} }
            .andReturn()

        assert(animals.response.contentAsString.contains("$newNum"))
        assert(animals.response.contentAsString.contains("Another dog"))
    }

    @Test
    fun shouldDeleteAnimalIntegrationTest() {
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

        val newNum = nextInt()

        // Adding the animal before deleting it.
        mockMvc.post("/api/shelter/new") {
            theCookie?.let { cookie(it) }
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"number\": $newNum,\n" +
                    "    \"name\": \"Delete me\",\n" +
                    "    \"age\": 4,\n" +
                    "    \"breed\": \"Golden Retriever\",\n" +
                    "    \"health\": \"Dead :(\"\n" +
                    "}"
        }
            .andExpect { status { is2xxSuccessful() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()

        val result = mockMvc.delete("/api/shelter/delete?number=$newNum") {
            theCookie?.let { cookie(it) }
        }
            .andExpect { status { isOk() } }
            .andReturn()

        assert(result.response.contentAsString == "Successful deletion.")
        assertFalse(result.response.contentAsString == "Deletion not complete. Animal number does not exist.")
    }

}