package no.kristiania.pg5100exam.services.user

import no.kristiania.pg5100exam.models.user.AuthorityEntity
import no.kristiania.pg5100exam.repos.user.AuthorityRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService(@Autowired private val authorityRepo: AuthorityRepo) {

    fun getAuthorities(): List<AuthorityEntity> {
        return authorityRepo.findAll()
    }

    fun getAuthority(title: String): AuthorityEntity? {
        return authorityRepo.getAuthority(title)
    }

}