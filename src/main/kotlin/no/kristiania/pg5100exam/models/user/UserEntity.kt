package no.kristiania.pg5100exam.models.user

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(
        name = "users_id_seq",
        allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "username")
    val username: String?,

    @Column(name = "created")
    val created: LocalDateTime = LocalDateTime.now(),

    @Column(name = "enabled")
    val enabled: Boolean = true,

    @Column(name = "password")
    var password: String? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_authorities",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "authority_id")]
    )
    val authorities: MutableList<AuthorityEntity> = mutableListOf()
)
