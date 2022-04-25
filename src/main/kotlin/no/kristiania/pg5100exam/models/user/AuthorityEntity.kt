package no.kristiania.pg5100exam.models.user

import javax.persistence.*

@Entity
@Table(name = "authorities")
data class AuthorityEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorities_id_seq")
    @SequenceGenerator(
        name = "authorities_id_seq",
        allocationSize = 1
    )
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "title")
    val title: String?
)