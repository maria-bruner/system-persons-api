package com.system.person_api.controller;

import com.system.person_api.dto.ReportDTO;
import com.system.person_api.model.ReportModel;
import com.system.person_api.repository.ReportRepository;
import com.system.person_api.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/person-reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;

    @GetMapping
    public Page<ReportModel> getReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return reportService.getReports(page, size);
    }

    @PostMapping
    public ResponseEntity<ReportDTO> createReport() {
        ReportDTO createdReport = reportService.createReportAndSendMessageQueue();
        return ResponseEntity.status(201).body(createdReport);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> getReport(@PathVariable UUID id) {
        Optional<ReportModel> report = reportRepository.findById(id);
        if (report.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + report.get().getName());
            headers.setContentType(MediaType.parseMediaType("text/csv"));

            ByteArrayInputStream inputStream = new ByteArrayInputStream(report.get().getContent());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new InputStreamResource(inputStream));
        }

        return ResponseEntity.notFound().build();
    }
}