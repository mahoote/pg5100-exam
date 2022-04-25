package no.kristiania.pg5100exam.controllers.animal

import no.kristiania.pg5100exam.models.animal.AnimalBreedEntity
import no.kristiania.pg5100exam.services.animal.AnimalBreedService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/shelter/breed")
class AnimalBreedController(@Autowired private val animalBreedService: AnimalBreedService) {

    @GetMapping("/all")
    fun getBreeds(): ResponseEntity<List<AnimalBreedEntity>> {
        return ResponseEntity.ok().body(animalBreedService.getBreeds())
    }

}