package com.system.person_api.service;

import com.system.person_api.dto.MessageDTO;
import com.system.person_api.dto.ReportDTO;
import com.system.person_api.model.ReportModel;
import com.system.person_api.model.ReportStatus;
import com.system.person_api.publisher.MessagePublisher;
import com.system.person_api.repository.ReportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ReportService {


    private final ReportRepository personReportRepository;
    private final MessagePublisher messagePublisher;

    public ReportService(ReportRepository personReportRepository, MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
        this.personReportRepository = personReportRepository;
    }

    /**
     * Busca os relatórios.
     *
     * @param page O número da página a ser retornada.
     * @param size O número de itens por página.
     * @return Uma página de relatórios.
     */
    public Page<ReportModel> getReports(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return personReportRepository.findAll(pageable);
    }

    /**
     * Gera um nome de arquivo único para o relatório.
     *
     * @return O nome do arquivo do relatório.
     */
    private String getFileName() {
        return "Relatório de Pesssoas - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".csv";
    }

    /**
     * Cria um novo relatório e envia uma mensagem para a fila de mensagens.
     *
     * @return Um {@link ReportDTO}.
     */
    public ReportDTO createReportAndSendMessageQueue() {
        ReportModel reportModel = new ReportModel(
                getFileName(),
                ReportStatus.PENDING,
                LocalDateTime.now()
        );
        reportModel = personReportRepository.save(reportModel);
        sendReportMessage(reportModel);
        return new ReportDTO().fromReportModel(reportModel);
    }

    /**
     * Salva um relatório.
     *
     * @param reportDTO Os dados do relatório a serem salvos.
     * @return O relatório salvo.
     */
    public ReportModel saveReport(ReportDTO reportDTO) {
        ReportModel reportModel = reportDTO.toReportModel();
        return personReportRepository.save(reportModel);
    }

    /**
     * Envia uma mensagem para a fila com o ID do relatório.
     *
     * @param reportModel O relatório que será enviado na mensagem.
     */
    public void sendReportMessage(ReportModel reportModel) {
        MessageDTO message = new MessageDTO(reportModel.getId());
        messagePublisher.sendMessage(message);
    }
}