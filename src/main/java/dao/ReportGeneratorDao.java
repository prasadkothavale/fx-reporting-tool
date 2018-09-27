package dao;

import java.util.Calendar;
import java.util.List;

import domain.Instruction;
import domain.Report;

public interface ReportGeneratorDao {
	
	/**
	 * @param settlementDate
	 * @return {@link List} of {@link Instruction} for specified settlement date
	 */
	List<Instruction> findInstructionsBySettlementDate(Calendar settlementDate);
	
	/**
	 * Saves generated {@link Report} for specified settlement date
	 * 
	 * @param settlementDate
	 * @param report
	 * @return
	 */
	Report saveReport(Calendar settlementDate, Report report);
	
	/**
	 * @param settlementDate
	 * @return {@link Report} for specified settlement date
	 */
	Report findReportBySettlementDate(Calendar settlementDate);
}
