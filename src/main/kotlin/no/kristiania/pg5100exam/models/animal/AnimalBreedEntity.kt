package no.kristiania.pg5100exam.models.animal

import javax.persistence.*

@Entity
@Table(name = "animal_breeds")
data class AnimalBreedEntity(
    @Id
    @SequenceGenerator(
        name = "animal_breeds_id_seq",
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_breeds_id_seq")
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "breed")
    val breed: String,

    // Does not exist in the database. Is only assigned to when fetching.
    @ManyToOne(targetEntity = AnimalTypeEntity::class, fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", referencedColumnName = "id", insertable = false, updatable = false)
    val type: AnimalTypeEntity? = null,

    // Is used to get the correct AnimalTypeEntity.
    @Column(name = "type_id")
    val typeId: Long?,
)
