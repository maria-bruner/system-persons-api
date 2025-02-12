package com.system.person_api.service;

import com.system.person_api.model.PersonModel;
import com.system.person_api.model.ReportModel;
import com.system.person_api.model.ReportStatus;
import com.system.person_api.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class ReportCSVServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private PersonService personService;

    private ReportCSVService reportCSVService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reportCSVService = new ReportCSVService(reportRepository, personService);
    }

    @Test
    void shouldGenerateCSVAndSaveReportSuccessfully() throws IOException {
        UUID reportId = UUID.randomUUID();
        ReportModel reportModel = new ReportModel(reportId, "Report 1", ReportStatus.PENDING, null, null, null);
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(reportModel));

        PersonModel person = new PersonModel();
        person.setName("Maria Carolina");
        person.setPhone("123456789");
        person.setCpf("123.456.789-00");
        person.setAddress("Rua Amazonas");
        person.setNumber("10");
        person.setComplementary("Apt 1");
        person.setCep("12345-678");
        person.setNeighborhood("Possui Campainha");
        person.setCity("Blumenau");
        person.setState("Santa Catarina");

        when(personService.getAllPersonsSortedByName()).thenReturn(List.of(person));

        Path mockPath = mock(Path.class);
        File mockFile = mock(File.class);
        when(mockFile.getName()).thenReturn("test.csv");
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.toPath()).thenReturn(mockPath);

        reportCSVService.generateAndSaveCSV(reportId);

        verify(reportRepository, times(1)).save(argThat(report -> report.getStatus() == ReportStatus.COMPLETED));
    }

    @Test
    void shouldLogErrorWhenReportNotFound() {
        UUID reportId = UUID.randomUUID();
        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

        reportCSVService.generateAndSaveCSV(reportId);

        verify(reportRepository, never()).save(any());
    }

    @Test
    void shouldSaveReportWithCompletedStatus() throws IOException {
        UUID reportId = UUID.randomUUID();
        ReportModel reportModel = new ReportModel(reportId, "Report 3", ReportStatus.PENDING, null, null, null);
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(reportModel));

        PersonModel person = new PersonModel();
        person.setName("Maria Carolina");
        person.setPhone("987654321");
        person.setCpf("987.654.321-00");
        person.setAddress("Rua Amazonas");
        person.setNumber("10");
        person.setComplementary("Apt 2");
        person.setCep("87654-321");
        person.setNeighborhood("Sem complemento");
        person.setCity("Blumenau");
        person.setState("Santa Catarina");

        when(personService.getAllPersonsSortedByName()).thenReturn(List.of(person));

        Path mockPath = mock(Path.class);
        File mockFile = mock(File.class);
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.toPath()).thenReturn(mockPath);

        reportCSVService.generateAndSaveCSV(reportId);

        verify(reportRepository, times(1)).save(argThat(report -> report.getStatus() == ReportStatus.COMPLETED));
    }
}