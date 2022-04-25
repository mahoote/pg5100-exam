package no.kristiania.pg5100exam.controllers.user

import no.kristiania.pg5100exam.models.user.AuthorityEntity
import no.kristiania.pg5100exam.services.user.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/authority")
class AuthController(@Autowired private val authService: AuthService) {

    @GetMapping("/all")
    fun getAuthorities(): ResponseEntity<List<AuthorityEntity>> {
        return ResponseEntity.ok().body(authService.getAuthorities())
    }

    @PostMapping("/add")
    fun addAuthority(@RequestBody newAuthorityInfo: AuthorityInfo): ResponseEntity<AuthorityEntity> {
        val createdAuth = authService.addAuthority(newAuthorityInfo)
        val uri =
            URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/authority/add").toUriString())
        return ResponseEntity.created(uri).body(createdAuth)
    }

    @DeleteMapping("/delete")
    fun deleteAuthority(@RequestBody authorityInfo: AuthorityInfo) {
        authService.deleteAuthority(authorityInfo)
    }

}

data class AuthorityInfo(val title: String)
