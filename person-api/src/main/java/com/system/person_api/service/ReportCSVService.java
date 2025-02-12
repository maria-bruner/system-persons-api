package com.system.person_api.service;

import com.system.person_api.model.PersonModel;
import com.system.person_api.model.ReportModel;
import com.system.person_api.model.ReportStatus;
import com.system.person_api.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReportCSVService {

    private final ReportRepository personReportRepository;
    private final PersonService personService;

    public ReportCSVService(ReportRepository personReportRepository, PersonService personService) {
        this.personReportRepository = personReportRepository;
        this.personService = personService;
    }

    /**
     * Cria um arquivo CSV com informações das pessoas ordenadas por nome.
     *
     * @param fileName O nome do arquivo CSV a ser gerado.
     * @return O arquivo CSV gerado.
     */
    private File createCSVByPersons(String fileName) throws IOException {
        List<PersonModel> persons = personService.getAllPersonsSortedByName();
        String tempDir = System.getProperty("java.io.tmpdir");

        File dir = new File(tempDir);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Não foi possível criar o diretório temporário: " + tempDir);
        }

        File csvFile = new File(tempDir, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.append("Name,Phone,CPF,Address,Number,Complementary,CEP,Neighborhood,City,State");
            writer.newLine();

            for (PersonModel person : persons) {
                writer.append(String.join(",", person.getName(), person.getPhone(), person.getCpf(),
                        person.getAddress(), person.getNumber(), person.getComplementary(),
                        person.getCep(), person.getNeighborhood(), person.getCity(), person.getState()));
                writer.newLine();
            }
        }
        return csvFile;
    }

    /**
     * Gera um arquivo CSV com informações das pessoas e salva o relatório no banco de dados.
     *
     * @param id O ID do relatório que será gerado.
     */
    public void generateAndSaveCSV(UUID id) {
        try {
            personReportRepository.findById(id)
                    .ifPresentOrElse(reportModel -> {
                        try {
                            File csvFile = createCSVByPersons(reportModel.getName());
                            byte[] contentFile = Files.readAllBytes(csvFile.toPath());

                            saveReport(reportModel, contentFile, ReportStatus.COMPLETED, null);
                        } catch (Exception exception) {
                            saveReport(reportModel, null, ReportStatus.FAIL, exception.getMessage());
                        }
                    }, () -> {
                    });
        } catch (Exception error) {
            System.out.println("Relatório com erro: " + error.getMessage());
        }
    }

    /**
     * Salva um relatório no banco de dados com o conteúdo gerado (arquivo CSV) e o status.
     *
     * @param reportModel O modelo do relatório
     * @param contentFile O conteúdo do arquivo CSV gerado
     * @param status O status do relatório
     * @param errorMessage A mensagem de erro
     */
    private void saveReport(ReportModel reportModel, byte[] contentFile, ReportStatus status, String errorMessage) {
        personReportRepository.save(new ReportModel(
                reportModel.getId(),
                reportModel.getName(),
                status,
                LocalDateTime.now(),
                contentFile,
                errorMessage
        ));
    }
}