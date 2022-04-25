package no.kristiania.pg5100exam.controllers.animal

import no.kristiania.pg5100exam.models.animal.AnimalEntity
import no.kristiania.pg5100exam.services.animal.AnimalService
import org.springframework.beans.factory.annotation.Autowired
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

    @PostMapping("/animal")
    fun getAnimals(@RequestBody animalInfo: AnimalInfo): ResponseEntity<AnimalEntity?> {
        val createdAnimal = animalService.getAnimalByName(animalInfo.name)
        val uri =
            URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/shelter/animal").toUriString())
        return ResponseEntity.created(uri).body(createdAnimal)
    }

}

data class AnimalInfo(val name: String)