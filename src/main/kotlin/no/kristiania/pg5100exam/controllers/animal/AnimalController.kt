package no.kristiania.pg5100exam.controllers.animal

import no.kristiania.pg5100exam.models.animal.AnimalEntity
import no.kristiania.pg5100exam.services.animal.AnimalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/shelter")
class AnimalController(@Autowired private val animalService: AnimalService) {

    @GetMapping("/all")
    fun getAnimals(): ResponseEntity<List<AnimalEntity>> {
        return ResponseEntity.ok().body(animalService.getAnimals())
    }

    @GetMapping("/animal")
    fun getAnimal(@RequestParam number: String): ResponseEntity<AnimalEntity?> {
        val createdAnimal = animalService.getAnimalByNumber(number.toLong())
        val uri =
            URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/shelter/animal").toUriString())
        return ResponseEntity.created(uri).body(createdAnimal)
    }

    @PostMapping("/new")
    fun addAnimal(@RequestBody animalInfo: AnimalInfo): ResponseEntity<AnimalEntity?> {
        val createdAnimal = animalService.addAnimal(animalInfo)
        val uri =
            URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/shelter/new").toUriString())

        if(createdAnimal != null)
            return ResponseEntity.created(uri).body(createdAnimal)

        // Will return 422 due to breed being null.
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build()
    }

}

data class AnimalInfo(val id: Long? = null, val number: Long? = null, val name: String? = null, val age: Int? = null, val breed: String? = null, val health: String? = null)