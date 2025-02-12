package com.system.person_api.service;

import com.system.person_api.dto.PersonDTO;
import com.system.person_api.exception.PersonNotFoundException;
import com.system.person_api.model.PersonModel;
import com.system.person_api.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    private PersonService personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personService = new PersonService(personRepository);
    }

    @Test
    void shouldGetPersonById() {
        UUID id = UUID.randomUUID();
        PersonModel person = new PersonModel();
        person.setId(id);
        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        Optional<PersonModel> result = personService.getPersonById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(personRepository, times(1)).findById(id);
    }

    @Test
    void shouldReturnEmptyWhenPersonNotFoundById() {
        UUID id = UUID.randomUUID();
        when(personRepository.findById(id)).thenReturn(Optional.empty());

        Optional<PersonModel> result = personService.getPersonById(id);

        assertFalse(result.isPresent());
        verify(personRepository, times(1)).findById(id);
    }

    @Test
    void shouldCreatePerson() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setCpf("123.456.789-00");
        personDTO.setName("Maria Carolina");
        personDTO.setPhone("123456789");
        personDTO.setAddress("Rua Amazonas");

        PersonModel personModel = new PersonModel();
        when(personRepository.save(any(PersonModel.class))).thenReturn(personModel);

        PersonModel result = personService.createPerson(personDTO);

        assertNotNull(result);
        verify(personRepository, times(1)).save(any(PersonModel.class));
    }

    @Test
    void shouldUpdatePerson() throws PersonNotFoundException {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setCpf("123.456.789-00");
        personDTO.setName("Maria Carolina Atualizada");
        personDTO.setPhone("987654321");
        personDTO.setAddress("Rua Amazonas Atualizada");

        PersonModel existingPerson = new PersonModel();
        existingPerson.setCpf("123.456.789-00");
        existingPerson.setName("Maria Carolina");

        Pageable pageable = PageRequest.of(0, 1);
        when(personRepository.findByCpf(personDTO.getCpf(), pageable)).thenReturn(new PageImpl<>(List.of(existingPerson)));

        when(personRepository.save(any(PersonModel.class))).thenReturn(existingPerson);

        PersonModel result = personService.updatePerson(personDTO);

        assertEquals("Maria Carolina Atualizada", result.getName());
        assertEquals("987654321", result.getPhone());
        verify(personRepository, times(1)).save(any(PersonModel.class));
    }

    @Test
    void shouldThrowPersonNotFoundExceptionWhenUpdatingPerson() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setCpf("123.456.789-00");

        when(personRepository.findByCpf(personDTO.getCpf(), PageRequest.of(0, 1))).thenReturn(new PageImpl<>(List.of()));

        assertThrows(PersonNotFoundException.class, () -> personService.updatePerson(personDTO));
    }

    @Test
    void shouldDeletePersonById() {
        UUID id = UUID.randomUUID();
        doNothing().when(personRepository).deleteById(id);

        personService.deletePersonById(id);

        verify(personRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldGetAllPersonsSortedByName() {
        personService.getAllPersonsSortedByName();

        verify(personRepository, times(1)).findAll(any(Sort.class));
    }
}