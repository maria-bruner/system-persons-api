package com.system.person_api.service;

import com.system.person_api.dto.ReportDTO;
import com.system.person_api.model.ReportModel;
import com.system.person_api.model.ReportStatus;
import com.system.person_api.publisher.MessagePublisher;
import com.system.person_api.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private MessagePublisher messagePublisher;

    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        reportService = new ReportService(reportRepository, messagePublisher);
    }

    @Test
    void shouldGetReports() {
        int page = 0;
        int size = 10;

        ReportModel reportModel = new ReportModel("Report-1", ReportStatus.PENDING, LocalDateTime.now());
        Page<ReportModel> reportPage = new PageImpl<>(List.of(reportModel), PageRequest.of(page, size), 1);
        when(reportRepository.findAll(PageRequest.of(page, size))).thenReturn(reportPage);

        Page<ReportModel> result = reportService.getReports(page, size);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(reportRepository, times(1)).findAll(PageRequest.of(page, size));
    }

    @Test
    void shouldCreateReportAndSendMessageQueue() {
        ReportModel reportModel = new ReportModel("Report-1", ReportStatus.PENDING, LocalDateTime.now());
        when(reportRepository.save(any(ReportModel.class))).thenReturn(reportModel);

        ReportDTO result = reportService.createReportAndSendMessageQueue();

        assertNotNull(result);
        verify(reportRepository, times(1)).save(any(ReportModel.class));
        verify(messagePublisher, times(1)).sendMessage(any());
    }

    @Test
    void shouldSaveReport() {
        ReportDTO reportDTO = new ReportDTO();
        ReportModel reportModel = new ReportModel("Report-1", ReportStatus.PENDING, LocalDateTime.now());
        when(reportRepository.save(any(ReportModel.class))).thenReturn(reportModel);

        ReportModel result = reportService.saveReport(reportDTO);

        assertNotNull(result);
        verify(reportRepository, times(1)).save(any(ReportModel.class));
    }

    @Test
    void shouldSendReportMessage() {
        ReportModel reportModel = new ReportModel("Report-1", ReportStatus.PENDING, LocalDateTime.now());
        doNothing().when(messagePublisher).sendMessage(any());

        reportService.sendReportMessage(reportModel);

        verify(messagePublisher, times(1)).sendMessage(any());
    }
}