package no.kristiania.pg5100exam.models.animal

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "animals")
data class AnimalEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animals_id_seq")
    @SequenceGenerator(
        name = "animals_id_seq",
        allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "animal_name")
    val name: String?,

    @Column(name = "age")
    val age: Int?,

    // Does not exist in the database. Is only assigned to when fetching.
    @ManyToOne(targetEntity = AnimalBreedEntity::class, fetch = FetchType.EAGER)
    @JoinColumn(name = "breed_id", referencedColumnName = "id", insertable = false, updatable = false)
    val breed: AnimalBreedEntity? = null,

    // Is used to get the correct AnimalBreedEntity.
    @Column(name = "breed_id")
    val breedId: Long?,

    @Column(name = "health")
    val health: String?,

    @Column(name = "created")
    val created: LocalDateTime = LocalDateTime.now()
)
