package com.system.person_api.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PersonModelTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateValidPersonModel() {
        PersonModel person = new PersonModel(
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
        assertTrue(validator.validate(person).isEmpty());
    }

    @Test
    void shouldFailWhenNameIsNull() {
        PersonModel person = new PersonModel();
        person.setCpf("123.456.789-00");
        assertTrue(validator.validate(person).stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void shouldFailWhenCpfIsNull() {
        PersonModel person = new PersonModel();
        person.setName("Maria Carolina");
        assertTrue(validator.validate(person).stream().anyMatch(v -> v.getPropertyPath().toString().equals("cpf")));
    }
}