package no.kristiania.pg5100exam.controllers.animal

import no.kristiania.pg5100exam.models.animal.AnimalEntity
import no.kristiania.pg5100exam.services.animal.AnimalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.time.LocalDateTime

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
        return ResponseEntity.ok().body(createdAnimal)
    }

    @PostMapping("/new")
    fun addAnimal(@RequestBody animalInfo: AnimalInfo): ResponseEntity<AnimalEntity?> {
        val createdAnimal = animalService.addAnimal(animalInfo)
        val uri =
            URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/shelter/new").toUriString())
        return sendCreatedResponse(createdAnimal, uri)

    }

    @PutMapping("/update")
    fun updateAnimal(@RequestBody animalInfo: AnimalInfo): ResponseEntity<AnimalEntity?> {
        val updatedAnimal = animalService.updateAnimal(animalInfo)
        val uri =
            URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/shelter/update").toUriString())
        return sendCreatedResponse(updatedAnimal, uri)
    }

    @DeleteMapping("/delete")
    fun deleteAnimal(@RequestParam number: Long): ResponseEntity<String> {
        val deletedAnimal = animalService.deleteAnimal(number)
        return ResponseEntity.ok().body(deletedAnimal)
    }


    private fun sendCreatedResponse(animal: AnimalEntity?, uri: URI): ResponseEntity<AnimalEntity?> {
        if(animal != null)
            return ResponseEntity.created(uri).body(animal)

        return ResponseEntity.badRequest().build()
    }

}

data class AnimalInfo(val id: Long? = null, val number: Long? = null, val name: String? = null, val age: Int? = null, val breed: String? = null, val health: String? = null, val created: LocalDateTime? = null)