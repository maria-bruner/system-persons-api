package com.system.person_api.repository;

import com.system.person_api.model.ReportModel;
import com.system.person_api.model.ReportStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    @Test
    @Transactional
    void shouldSaveAndFindReportsByStatus() {
        ReportModel report = new ReportModel();
        report.setStatus(ReportStatus.PENDING);
        report.setCreatedDate(LocalDateTime.now());
        report.setName("Report 1");

        reportRepository.save(report);

        Pageable pageable = PageRequest.of(0, 10);
        var page = reportRepository.findAllByStatusOrderByCreatedDateDesc(ReportStatus.PENDING, pageable);

        assertFalse(page.isEmpty(), "Deve encontrar relatórios com o status PENDING.");
        assertEquals(1, page.getTotalElements(), "Deve haver 1 relatório com status PENDING.");
    }

    @Test
    @Transactional
    void shouldFindReportsByStatusOrderedByDate() {
        ReportModel report1 = new ReportModel();
        report1.setStatus(ReportStatus.PENDING);
        report1.setCreatedDate(LocalDateTime.of(2021, 7, 10, 10, 0, 0));
        report1.setName("Report 1");

        ReportModel report2 = new ReportModel();
        report2.setStatus(ReportStatus.PENDING);
        report2.setCreatedDate(LocalDateTime.of(2021, 7, 11, 10, 0, 0));
        report2.setName("Report 2");

        reportRepository.save(report1);
        reportRepository.save(report2);

        Pageable pageable = PageRequest.of(0, 10);
        var page = reportRepository.findAllByStatusOrderByCreatedDateDesc(ReportStatus.PENDING, pageable);

        assertEquals(2, page.getTotalElements(), "Deve haver 2 relatórios.");
        assertEquals(report2.getId(), page.getContent().get(0).getId(), "O relatório mais recente deve vir primeiro.");
        assertEquals(report1.getId(), page.getContent().get(1).getId(), "O relatório mais antigo deve vir depois.");
    }
}