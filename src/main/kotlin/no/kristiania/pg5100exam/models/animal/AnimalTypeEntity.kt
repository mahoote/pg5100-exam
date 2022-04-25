package no.kristiania.pg5100exam.models.animal

import javax.persistence.*

@Entity
@Table(name = "animal_types")
data class AnimalTypeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_types_id_seq")
    @SequenceGenerator(
        name = "animal_types_id_seq",
        allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "animal_type")
    val type: String,
)
