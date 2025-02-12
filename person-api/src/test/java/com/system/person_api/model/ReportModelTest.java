package com.system.person_api.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReportModelTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateValidReportModel() {
        ReportModel report = new ReportModel(
                UUID.randomUUID(), "Relatório de Pessoa", ReportStatus.COMPLETED, LocalDateTime.now(), new byte[]{1, 2, 3}, null
        );
        assertTrue(validator.validate(report).isEmpty());
    }

    @Test
    void shouldFailWhenStatusIsNull() {
        ReportModel report = new ReportModel(UUID.randomUUID(), "Relatório de Pessoa", null, LocalDateTime.now(), null, null);
        assertTrue(validator.validate(report).stream().anyMatch(v -> v.getPropertyPath().toString().equals("status")));
    }

    @Test
    void shouldFailWhenCreatedDateIsNull() {
        ReportModel report = new ReportModel(UUID.randomUUID(), "Relatório de Pessoa", ReportStatus.PENDING, null, null, null);
        assertTrue(validator.validate(report).stream().anyMatch(v -> v.getPropertyPath().toString().equals("createdDate")));
    }

    @Test
    void shouldFailWhenNameIsNull() {
        ReportModel report = new ReportModel(UUID.randomUUID(), null, ReportStatus.COMPLETED, LocalDateTime.now(), null, null);
        assertTrue(validator.validate(report).stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }
}