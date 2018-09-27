package service;

import java.time.LocalDate;
import java.util.Calendar;

import domain.Instruction;
import domain.Report;

public interface ReportGeneratorService {

    /**
     * @param instruction
     */
    void saveInstruction(Instruction instruction);

    /**
     * Pulls {@link Instructions} for specified settlement date and generates @{link Report}
     * by processing the instructions
     *
     * @param settlementDate
     * @return
     */
    Report generateReport(LocalDate settlementDate);
}
