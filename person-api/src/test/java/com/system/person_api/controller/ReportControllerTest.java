package com.system.person_api.controller;

import com.system.person_api.dto.ReportDTO;
import com.system.person_api.model.ReportModel;
import com.system.person_api.repository.ReportRepository;
import com.system.person_api.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReportService reportService;

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportController reportController;

    private ReportDTO reportDTO;
    private ReportModel reportModel;
    private UUID id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();

        id = UUID.randomUUID();
        reportDTO = new ReportDTO();
        reportDTO.setId(id);
        reportDTO.setName("Report 1");

        reportModel = new ReportModel();
        reportModel.setId(id);
        reportModel.setName("Report 1");
        reportModel.setContent("Report content".getBytes());
    }

    @Test
    void shouldCreateReport() throws Exception {
        when(reportService.createReportAndSendMessageQueue()).thenReturn(reportDTO);

        mockMvc.perform(post("/api/person-reports")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(reportDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Report 1"));

        verify(reportService, times(1)).createReportAndSendMessageQueue();
    }

    @Test
    void shouldDownloadReport() throws Exception {
        when(reportRepository.findById(id)).thenReturn(Optional.of(reportModel));

        mockMvc.perform(get("/api/person-reports/download/{id}", id))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Report 1"))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "text/csv"))
                .andExpect(content().bytes("Report content".getBytes()));

        verify(reportRepository, times(1)).findById(id);
    }

    @Test
    void shouldReturnNotFoundWhenReportDoesNotExist() throws Exception {
        when(reportRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/person-reports/download/{id}", id))
                .andExpect(status().isNotFound());

        verify(reportRepository, times(1)).findById(id);
    }
}