package no.kristiania.pg5100exam.repos.user

import no.kristiania.pg5100exam.models.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo: JpaRepository<UserEntity, Long> {

    fun findByUsername(username: String): UserEntity?

}