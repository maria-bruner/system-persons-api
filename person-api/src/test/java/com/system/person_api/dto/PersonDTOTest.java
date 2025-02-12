package com.system.person_api.dto;

import com.system.person_api.model.PersonModel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PersonDTOTest {

    @Test
    void testFromPersonModel() {
        PersonModel personModel = new PersonModel(
                UUID.randomUUID(),
                "Maria Carolina",
                "123456789",
                "123.456.789-00",
                "Rua Amazonas",
                "10",
                "Apt 1",
                "12345-678",
                "Garcia",
                "Blumenau",
                "Santa Catarina"
        );

        PersonDTO personDTO = PersonDTO.fromPersonModel(personModel);

        assertAll(
                () -> assertEquals(personModel.getId(), personDTO.getId()),
                () -> assertEquals(personModel.getName(), personDTO.getName()),
                () -> assertEquals(personModel.getCpf(), personDTO.getCpf()),
                () -> assertEquals(personModel.getAddress(), personDTO.getAddress())
        );
    }

    @Test
    void testToPersonModel() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(UUID.randomUUID());
        personDTO.setName("Maria Carolina");
        personDTO.setCpf("123.456.789-00");
        personDTO.setAddress("Rua Amazonas");

        PersonModel personModel = personDTO.toPersonModel();

        assertAll(
                () -> assertEquals(personDTO.getId(), personModel.getId()),
                () -> assertEquals(personDTO.getName(), personModel.getName()),
                () -> assertEquals(personDTO.getCpf(), personModel.getCpf()),
                () -> assertEquals(personDTO.getAddress(), personModel.getAddress())
        );
    }
}