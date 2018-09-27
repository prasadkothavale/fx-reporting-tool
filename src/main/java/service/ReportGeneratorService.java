package service;

import java.util.Calendar;

import domain.Report;

public interface ReportGeneratorService {

	/**
	 * Pulls {@link Instructions} for specified settlement date and generates @{link Report}
	 * by processing the instructions
	 * 
	 * @param settlementDate
	 * @return
	 */
	Report generateReport(Calendar settlementDate);
	
	/**
	 * Returns {@link Report} for specified settlement date
	 * 
	 * @param settlementDate
	 * @return
	 */
	Report findReportBySettlementDate(Calendar settlementDate);
}
