package no.kristiania.pg5100exam.controllers.user

import no.kristiania.pg5100exam.models.user.UserEntity
import no.kristiania.pg5100exam.services.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(@Autowired private val userService: UserService) {

    @GetMapping("/all")
    fun getAuthorities(): ResponseEntity<List<UserEntity>> {
        return ResponseEntity.ok().body(userService.getUsers())
    }

}