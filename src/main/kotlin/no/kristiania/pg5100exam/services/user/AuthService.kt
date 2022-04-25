package no.kristiania.pg5100exam.services.user

import no.kristiania.pg5100exam.controllers.user.AuthorityInfo
import no.kristiania.pg5100exam.models.user.AuthorityEntity
import no.kristiania.pg5100exam.repos.user.AuthorityRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService(@Autowired private val authorityRepo: AuthorityRepo) {

    fun getAuthorities(): List<AuthorityEntity> {
        return authorityRepo.findAll()
    }

    fun getAuthorityByTitle(title: String): AuthorityEntity? {
        return authorityRepo.getByTitle(title)
    }

    fun addAuthority(newAuthorityInfo: AuthorityInfo): AuthorityEntity {
        val newAuthority = AuthorityEntity(null, newAuthorityInfo.title)
        return authorityRepo.save(newAuthority)
    }

    fun deleteAuthority(authorityInfo: AuthorityInfo) {
        val existingAuthority = getAuthorityByTitle(authorityInfo.title)
        existingAuthority?.id?.let { authorityRepo.deleteById(it) }
    }

}