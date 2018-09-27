package dao;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import domain.Instruction;
import domain.Report;

public interface ReportGeneratorDao {

	/**
	 * @param instruction
	 */
	Instruction saveInstruction(Instruction instruction);
	
	/**
	 * @param settlementDate
	 * @return {@link List} of {@link Instruction} for specified settlement date
	 */
	List<Instruction> findInstructionsBySettlementDate(LocalDate settlementDate);
}
