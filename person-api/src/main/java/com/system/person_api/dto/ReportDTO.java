package com.system.person_api.dto;

import com.system.person_api.model.ReportModel;
import com.system.person_api.model.ReportStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReportDTO {

    private UUID id;
    private String name;
    private ReportStatus status;
    private LocalDateTime createdDate;
    byte[] content;
    String logError;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getLogError() {
        return logError;
    }

    public void setLogError(String logError) {
        this.logError = logError;
    }

    public ReportDTO fromReportModel(ReportModel reportModel) {
        ReportDTO dto = new ReportDTO();
        dto.setId(reportModel.getId());
        dto.setName(reportModel.getName());
        dto.setStatus(reportModel.getStatus());
        dto.setCreatedDate(reportModel.getCreatedDate());
        dto.setLogError(reportModel.getLogError());
        dto.setContent(reportModel.getContent());
        return dto;
    }

    public ReportModel toReportModel() {
        return new ReportModel(
                this.id,
                this.name,
                this.status,
                this.createdDate,
                this.content,
                this.logError
        );
    }
}