package com.system.person_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reports")
public class ReportModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_report")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ReportStatus status;

    @CreatedDate
    @NotNull
    private LocalDateTime createdDate;

    @Lob
    private byte[] content;

    private String logError;

    @NotNull
    private String name;

    public ReportModel() {}

    public ReportModel(UUID id, String name, ReportStatus status, LocalDateTime createdDate, byte[] content, String logError) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.createdDate = createdDate;
        this.content = content;
        this.logError = logError;
    }

    public ReportModel(String name, ReportStatus status, LocalDateTime createdDate) {
        this.name = name;
        this.status = status;
        this.createdDate = createdDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}