package com.system.person_api.repository;

import com.system.person_api.model.ReportModel;
import com.system.person_api.model.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ReportRepository extends JpaRepository<ReportModel, UUID> {

    Page<ReportModel> findAllByStatusOrderByCreatedDateDesc(ReportStatus status, Pageable pageable);

}