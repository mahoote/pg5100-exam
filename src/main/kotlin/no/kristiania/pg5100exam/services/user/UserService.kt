package no.kristiania.pg5100exam.services.user

import no.kristiania.pg5100exam.controllers.user.UserInfo
import no.kristiania.pg5100exam.models.user.UserEntity
import no.kristiania.pg5100exam.repos.user.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired private val userRepo: UserRepo,
    @Autowired private val authService: AuthService

): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        username?.let {
            val user = userRepo.findByUsername(it)
            if (user != null) {
                return User(
                    user.username,
                    user.password,
                    user.authorities.map { authority -> SimpleGrantedAuthority(authority.title) })
            }
        }
        throw Exception("Bad")
    }

    fun getUsers(): List<UserEntity>? {
        return userRepo.findAll()
    }

    fun registerUser(userInfo: UserInfo): UserEntity? {
        val existingUser = userRepo.findByUsername(userInfo.username)

        if(existingUser == null) {
            val newUser = UserEntity(username = userInfo.username, password = BCryptPasswordEncoder().encode(userInfo.password))
            authService.getAuthority("USER")?.let { newUser.authorities.add(it) }
            return userRepo.save(newUser)
        }

        return null
    }

}
