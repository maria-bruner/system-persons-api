package com.system.person_api.repository;

import com.system.person_api.model.PersonModel;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    private PersonModel createPerson(String cpf, String name) {
        PersonModel person = new PersonModel();
        person.setCpf(cpf);
        person.setName(name);
        person.setPhone("123456789");
        person.setAddress("Rua Amazonas");
        person.setCity("Blumenau");
        person.setState("Santa Catarina");
        return person;
    }

    @Transactional
    @Test
    void shouldSaveAndFindPersonByCpf() {
        PersonModel person = createPerson("123.456.789-00", "Maria Carolina");
        personRepository.save(person);

        Pageable pageable = PageRequest.of(0, 10);
        var page = personRepository.findByCpf("123.456.789-00", pageable);

        assertFalse(page.isEmpty(), "Nenhuma pessoa encontrada com o CPF informado");
        assertEquals(1, page.getTotalElements(), "O total de elementos não corresponde");
        Assertions.assertEquals("123.456.789-00", page.getContent().get(0).getCpf(), "CPF não existe");
    }

    @Test
    void shouldFindPersonByNameContaining() {
        PersonModel person = createPerson("123.456.789-00", "Maria Carolina");
        personRepository.save(person);

        Pageable pageable = PageRequest.of(0, 10);
        var page = personRepository.findByNameContainingIgnoreCase("Maria", pageable);

        assertFalse(page.isEmpty());
        assertEquals(1, page.getTotalElements());
    }

    @Test
    void shouldDeletePersonById() {
        PersonModel person = createPerson("123.456.789-00", "Maria Carolina");
        personRepository.save(person);
        UUID id = person.getId();

        personRepository.deleteById(id);

        assertFalse(personRepository.existsById(id));
    }
}