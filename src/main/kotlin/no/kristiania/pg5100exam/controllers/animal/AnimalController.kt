package no.kristiania.pg5100exam.controllers.animal

import no.kristiania.pg5100exam.models.animal.AnimalEntity
import no.kristiania.pg5100exam.services.animal.AnimalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/shelter")
class AnimalController(@Autowired private val animalService: AnimalService) {

    @GetMapping("/all")
    fun getAnimals(): ResponseEntity<List<AnimalEntity>> {
        return ResponseEntity.ok().body(animalService.getAnimals())
    }

}