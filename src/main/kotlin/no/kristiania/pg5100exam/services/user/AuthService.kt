package no.kristiania.pg5100exam.services.user

import no.kristiania.pg5100exam.models.user.AuthorityEntity
import no.kristiania.pg5100exam.repos.user.AuthRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService(@Autowired private val authRepo: AuthRepo) {

    fun getAuthorities(): List<AuthorityEntity> {
        return authRepo.findAll()
    }

    fun getAuthority(title: String): AuthorityEntity? {
        return authRepo.getAuthorityByTitle(title)
    }

}