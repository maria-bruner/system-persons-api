package com.system.person_api.controller;

import com.system.person_api.dto.PersonDTO;
import com.system.person_api.model.PersonModel;
import com.system.person_api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public Page<PersonDTO> getPersons(
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return personService.getPersons(cpf, name, page, size);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonById(@PathVariable UUID id) {
        personService.deletePersonById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PersonModel> createPerson(@RequestBody PersonDTO personDTO) {
        PersonModel createdPerson = personService.createPerson(personDTO);
        return ResponseEntity.status(201).body(createdPerson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonModel> updatePerson(
            @PathVariable UUID id,
            @RequestBody PersonDTO personDTO) throws Exception {

        if (personDTO.getId() == null || !id.equals(personDTO.getId())) {
            return ResponseEntity.badRequest().build();
        }

        PersonModel updatedPerson = personService.updatePerson(personDTO);

        if (updatedPerson == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedPerson);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonModel> getPersonById(@PathVariable UUID id) {
        Optional<PersonModel> personOpt = personService.getPersonById(id);

        return personOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}