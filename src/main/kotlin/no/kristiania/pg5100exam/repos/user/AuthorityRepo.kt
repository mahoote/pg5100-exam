package no.kristiania.pg5100exam.repos.user

import no.kristiania.pg5100exam.models.user.AuthorityEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorityRepo: JpaRepository<AuthorityEntity, Long> {

    fun getAuthorityByTitle(title: String): AuthorityEntity?

}