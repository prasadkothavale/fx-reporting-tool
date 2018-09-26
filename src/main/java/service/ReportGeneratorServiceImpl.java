package service;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import constants.Direction;
import dao.ReportGeneratorDao;
import dao.ReportGeneratorDaoImpl;
import domain.Instruction;
import domain.Report;
import exceptions.FxReportException;

public class ReportGeneratorServiceImpl implements ReportGeneratorService {

	private static ReportGeneratorServiceImpl reportGeneratorServiceImpl;
	
	private ReportGeneratorDao reportGeneratorDao;
	
	private ReportGeneratorServiceImpl() throws FxReportException {
		reportGeneratorDao = ReportGeneratorDaoImpl.getInstance();
	}
	
	/**
	 * 
	 * @return Singleton instance of {@link ReportGeneratorServiceImpl}
	 * @throws FxReportException
	 */
	public static ReportGeneratorServiceImpl getInstance() throws FxReportException {
		if (reportGeneratorServiceImpl == null) {
			reportGeneratorServiceImpl = new ReportGeneratorServiceImpl();
		}
		return reportGeneratorServiceImpl;
	}
	
	/* (non-Javadoc)
	 * @see service.ReportGeneratorService#generateReport(java.util.Calendar)
	 */
	@Override
	public Report generateReport(Calendar settlementDate) {
		Report report = new Report();
		List<Instruction> instructions = reportGeneratorDao.findInstructionsBySettlementDate(settlementDate);
		
		// Calculate USD equivalent and separate out incoming and outgoing instructions
		instructions.stream().forEach(instruction -> {
			instruction.setUsdEquivalent(instruction.getAgreedFx() * instruction.getPricePerUnit() * instruction.getUnits());
			if (Direction.BUY.equals(instruction.getDirection())) {
				report.setOutgoingUSD(report.getOutgoingUSD() + instruction.getUsdEquivalent());
				report.getRankedOutgoingInstructions().add(instruction);
			} else {
				report.setIncomingUSD(report.getIncomingUSD() + instruction.getUsdEquivalent());
				report.getRankedIncomingInstructions().add(instruction);
			}
		});
		
		// Sort instructions based in USD equivalent and assign rank
		generateRank(report.getRankedIncomingInstructions());
		generateRank(report.getRankedOutgoingInstructions());
		
		return reportGeneratorDao.saveReport(settlementDate, report);
	}
	
	/**
	 * Sort instruction based on USD equivalent value to get rank
	 * 
	 * @param instructions
	 */
	private static void generateRank(List<Instruction> instructions) {
		Comparator<? super Instruction> comparator = new Comparator<Instruction>() {

			@Override
			public int compare(Instruction o1, Instruction o2) {
				return -1*(new Float(o1.getUsdEquivalent()).compareTo(o2.getUsdEquivalent()));
			}
		};
		instructions.sort(comparator);
		for (int rank = 0; rank < instructions.size(); rank++) {
			instructions.get(rank).setRank(rank+1);
		}
	}

	/* (non-Javadoc)
	 * @see service.ReportGeneratorService#findReportBySettlementDate(java.util.Calendar)
	 */
	@Override
	public Report findReportBySettlementDate(Calendar settlementDate) {
		return reportGeneratorDao.findReportBySettlementDate(settlementDate);
	}

	public void setReportGeneratorDao(ReportGeneratorDao reportGeneratorDao) {
		this.reportGeneratorDao = reportGeneratorDao;
	}

}
