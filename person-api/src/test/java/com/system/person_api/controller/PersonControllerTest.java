package com.system.person_api.controller;

import com.system.person_api.dto.PersonDTO;
import com.system.person_api.model.PersonModel;
import com.system.person_api.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PersonControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    private PersonDTO personDTO;
    private PersonModel personModel;
    private UUID id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();

        id = UUID.randomUUID();
        personDTO = new PersonDTO();
        personDTO.setId(id);
        personDTO.setName("Maria Carolina");
        personDTO.setPhone("11999999999");
        personDTO.setCpf("123.456.789-00");
        personDTO.setAddress("Rua Amazonas");
        personDTO.setNumber("123");
        personDTO.setComplementary("Bloco A");
        personDTO.setCep("12345-678");
        personDTO.setNeighborhood("Garci/a");
        personDTO.setCity("Blumenau");
        personDTO.setState("Santa Catarina");

        personModel = new PersonModel(
                id,
                "Maria Carolina",
                "33360000",
                "123.456.789-00",
                "Rua Amazonas",
                "123",
                "Bloco A",
                "12345-678",
                "Garcia",
                "Blumenau",
                "Santa Catarina"
        );
    }

    @Test
    void shouldReturnOkWhenGettingPersons() throws Exception {
        when(personService.getPersons(null, null, 0, 10)).thenReturn(null);

        mockMvc.perform(get("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(personService, times(1)).getPersons(null, null, 0, 10);
    }

    @Test
    void shouldReturnPersonWhenExists() throws Exception {
        when(personService.getPersonById(id)).thenReturn(Optional.of(personModel));

        mockMvc.perform(get("/api/persons/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));

        verify(personService, times(1)).getPersonById(id);
    }

    @Test
    void shouldReturnNotFoundWhenNotFound() throws Exception {
        when(personService.getPersonById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/persons/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(personService, times(1)).getPersonById(id);
    }

    @Test
    void shouldReturnCreatedWhenCreated() throws Exception {
        when(personService.createPerson(any(PersonDTO.class))).thenReturn(personModel);

        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(personDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()));

        verify(personService, times(1)).createPerson(any(PersonDTO.class));
    }

    @Test
    void shouldReturnOkWhenUpdated() throws Exception {
        personDTO.setId(id);
        when(personService.updatePerson(any(PersonDTO.class))).thenReturn(personModel);

        mockMvc.perform(put("/api/persons/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(personDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));

        verify(personService, times(1)).updatePerson(any(PersonDTO.class));
    }

    @Test
    void shouldReturnBadRequestWhenIdsMismatch() throws Exception {
        UUID wrongId = UUID.randomUUID();
        personDTO.setId(wrongId);

        mockMvc.perform(put("/api/persons/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(personDTO)))
                .andExpect(status().isBadRequest());

        verify(personService, never()).updatePerson(any(PersonDTO.class));
    }
}