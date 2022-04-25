package no.kristiania.pg5100exam.controllers.animal

import no.kristiania.pg5100exam.models.animal.AnimalTypeEntity
import no.kristiania.pg5100exam.services.animal.AnimalTypeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/shelter/type")
class AnimalTypeController(@Autowired private val animalTypeService: AnimalTypeService) {

    @GetMapping("/all")
    fun getTypes(): ResponseEntity<List<AnimalTypeEntity>> {
        return ResponseEntity.ok().body(animalTypeService.getTypes())
    }

}