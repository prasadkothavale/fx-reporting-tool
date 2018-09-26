package dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import constants.Direction;
import domain.Instruction;
import domain.Report;
import exceptions.FxReportException;

import static utils.Utils.toCalendar;
import static utils.Utils.adjustSettlementDate;

public class ReportGeneratorDaoImpl implements ReportGeneratorDao {
	
	private List<Instruction> instructions;
	private Map<Calendar, Report> reports;
	
	private static ReportGeneratorDaoImpl reportGeneratorDaoImpl;
	
	private ReportGeneratorDaoImpl () throws FxReportException {
		// Initialize data
		instructions = new ArrayList<>();
		reports = new HashMap<>();
		
		instructions.add(new Instruction("EO487I", Direction.BUY, 1.17f, "EUR", toCalendar("2018-08-31"), toCalendar("2018-09-01"), 370, 119.48f));
		instructions.add(new Instruction("OB527I", Direction.SELL, 0.014f, "INR", toCalendar("2018-09-01"), toCalendar("2018-09-02"), 597, 340.43f));
		instructions.add(new Instruction("YV787D", Direction.BUY, 0.27f, "AED", toCalendar("2018-09-01"), toCalendar("2018-09-02"), 600, 144.64f));
		instructions.add(new Instruction("DU783H", Direction.BUY, 0.27f, "AED", toCalendar("2018-09-01"), toCalendar("2018-09-03"), 244, 457.46f));
		instructions.add(new Instruction("WN587Z", Direction.BUY, 1.31f, "GBP", toCalendar("2018-09-02"), toCalendar("2018-09-03"), 194, 453.06f));
		instructions.add(new Instruction("HR757I", Direction.SELL, 1.00f, "USD", toCalendar("2018-09-02"), toCalendar("2018-09-04"), 402, 108.69f));
		instructions.add(new Instruction("KY987E", Direction.SELL, 1.00f, "USD", toCalendar("2018-09-02"), toCalendar("2018-09-04"), 152, 392.70f));
		instructions.add(new Instruction("FF797O", Direction.BUY, 1.31f, "GBP", toCalendar("2018-09-02"), toCalendar("2018-09-04"), 880, 114.65f));
		instructions.add(new Instruction("HD521P", Direction.BUY, 0.27f, "AED", toCalendar("2018-09-03"), toCalendar("2018-09-05"), 962, 144.86f));
		instructions.add(new Instruction("WM258Z", Direction.BUY, 0.73f, "SGD", toCalendar("2018-09-03"), toCalendar("2018-09-05"), 962, 268.22f));
		instructions.add(new Instruction("TD449K", Direction.SELL, 1.17f, "EUR", toCalendar("2018-09-03"), toCalendar("2018-09-05"), 732, 241.88f));
		instructions.add(new Instruction("VU485O", Direction.BUY, 0.73f, "SGD", toCalendar("2018-09-03"), toCalendar("2018-09-06"), 182, 215.88f));
		instructions.add(new Instruction("ES346P", Direction.BUY, 1.00f, "USD", toCalendar("2018-09-04"), toCalendar("2018-09-06"), 518, 230.28f));
		instructions.add(new Instruction("KK189R", Direction.SELL, 0.27f, "SAR", toCalendar("2018-09-04"), toCalendar("2018-09-06"), 385, 192.66f));
		instructions.add(new Instruction("IE465L", Direction.SELL, 1.31f, "GBP", toCalendar("2018-09-04"), toCalendar("2018-09-07"), 222, 450.30f));
		instructions.add(new Instruction("KD587Z", Direction.SELL, 0.73f, "SGD", toCalendar("2018-09-04"), toCalendar("2018-09-07"), 441, 205.95f));
		instructions.add(new Instruction("SG781A", Direction.BUY, 0.27f, "SAR", toCalendar("2018-09-04"), toCalendar("2018-09-07"), 822, 421.77f));
		instructions.add(new Instruction("RR602P", Direction.BUY, 1.17f, "EUR", toCalendar("2018-09-05"), toCalendar("2018-09-07"), 311, 494.61f));
		instructions.add(new Instruction("ZE995K", Direction.BUY, 1.31f, "GBP", toCalendar("2018-09-05"), toCalendar("2018-09-07"), 858, 176.85f));
		instructions.add(new Instruction("CH743U", Direction.SELL, 1.17f, "EUR", toCalendar("2018-09-05"), toCalendar("2018-09-06"), 468, 472.98f));
		instructions.add(new Instruction("NX981C", Direction.BUY, 0.27f, "AED", toCalendar("2018-09-05"), toCalendar("2018-09-06"), 743, 155.23f));
		instructions.add(new Instruction("JC299O", Direction.SELL, 0.27f, "AED", toCalendar("2018-09-05"), toCalendar("2018-09-07"), 268, 390.22f));
		instructions.add(new Instruction("CI696X", Direction.BUY, 0.27f, "AED", toCalendar("2018-09-06"), toCalendar("2018-09-07"), 526, 469.72f));
		instructions.add(new Instruction("TP464V", Direction.SELL, 0.73f, "SGD", toCalendar("2018-09-06"), toCalendar("2018-09-08"), 929, 446.39f));
		instructions.add(new Instruction("LF282J", Direction.BUY, 0.27f, "SAR", toCalendar("2018-09-06"), toCalendar("2018-09-08"), 560, 468.48f));
		instructions.add(new Instruction("US218R", Direction.BUY, 1.31f, "GBP", toCalendar("2018-09-06"), toCalendar("2018-09-08"), 591, 114.67f));
		instructions.add(new Instruction("KB554Y", Direction.SELL, 0.27f, "SAR", toCalendar("2018-09-07"), toCalendar("2018-09-08"), 733, 130.95f));
		instructions.add(new Instruction("TE954X", Direction.BUY, 0.014f, "INR", toCalendar("2018-09-07"), toCalendar("2018-09-08"), 126, 266.49f));
		instructions.add(new Instruction("WU649O", Direction.BUY, 1.00f, "USD", toCalendar("2018-09-07"), toCalendar("2018-09-08"), 589, 316.14f));
		
		instructions.stream().forEach(instruction -> {
			instruction.setSettlementDate(adjustSettlementDate(instruction.getSettlementDate(), instruction.getCurrency()));
		});
	}
	
	/**
	 * @return Singleton instance of {@link ReportGeneratorDaoImpl}
	 * @throws FxReportException
	 */
	public static ReportGeneratorDaoImpl getInstance() throws FxReportException {
		if (reportGeneratorDaoImpl == null) {
			reportGeneratorDaoImpl = new ReportGeneratorDaoImpl();
		}
		return reportGeneratorDaoImpl;
	}

	/* (non-Javadoc)
	 * @see dao.ReportGeneratorDao#findInstructionsBySettlementDate(java.util.Calendar)
	 */
	@Override
	public List<Instruction> findInstructionsBySettlementDate(Calendar settlementDate) {
		return instructions.stream().filter(instruction -> {
			return settlementDate.equals(instruction.getSettlementDate());
		}).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see dao.ReportGeneratorDao#saveReport(java.util.Calendar, domain.Report)
	 */
	@Override
	public Report saveReport(Calendar settlementDate, Report report) {
		return this.reports.put(settlementDate, report);
	}

	/* (non-Javadoc)
	 * @see dao.ReportGeneratorDao#findReportBySettlementDate(java.util.Calendar)
	 */
	@Override
	public Report findReportBySettlementDate(Calendar settlementDate) {
		return reports.get(settlementDate);
	}

}
