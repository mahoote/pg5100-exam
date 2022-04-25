package no.kristiania.pg5100exam.models.animal

import javax.persistence.*

@Entity
@Table(name = "animal_breeds")
data class AnimalBreedEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_breeds_id_seq")
    @SequenceGenerator(
        name = "animal_breeds_id_seq",
        allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "breed")
    val breed: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    val type: AnimalTypeEntity?,
)
