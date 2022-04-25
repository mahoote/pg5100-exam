package no.kristiania.pg5100exam.controllers.user

import no.kristiania.pg5100exam.models.user.AuthorityEntity
import no.kristiania.pg5100exam.services.user.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/authentication")
class AuthController(@Autowired private val authService: AuthService) {

    @GetMapping("/all")
    fun getAuthorities(): ResponseEntity<List<AuthorityEntity>> {
        return ResponseEntity.ok().body(authService.getAuthorities())
    }

}