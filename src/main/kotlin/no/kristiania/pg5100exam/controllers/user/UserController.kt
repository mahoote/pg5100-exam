package no.kristiania.pg5100exam.controllers.user

import no.kristiania.pg5100exam.models.user.UserEntity
import no.kristiania.pg5100exam.services.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/user")
class UserController(@Autowired private val userService: UserService) {

    @GetMapping("/all")
    fun getAuthorities(): ResponseEntity<List<UserEntity>> {
        return ResponseEntity.ok().body(userService.getUsers())
    }

    @PostMapping("/register")
    fun registerUser(@RequestBody userInfo: UserInfo): ResponseEntity<UserEntity> {
        val createdUser = userService.registerUser(userInfo)
        val uri =
            URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/register").toUriString())
        return ResponseEntity.created(uri).body(createdUser)
    }
}

data class UserInfo (val username: String, val password: String)
