package dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utils.Utils.toCalendar;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import constants.Constants;
import domain.Instruction;
import domain.Report;

public class ReportGeneratorDaoImplTest {

	private ReportGeneratorDaoImpl reportGeneratorDao;
	
	@Before
	public void setUp() throws Exception {
		reportGeneratorDao = ReportGeneratorDaoImpl.getInstance();
	}

	@Test
	public void testFindInstructionsBySettlementDate() throws Exception {
		// settlement date on Sunday
		List<Instruction> instructions = reportGeneratorDao.findInstructionsBySettlementDate(toCalendar("2018-09-02"));
		assertEquals(1, instructions.size());
		assertTrue(Constants.ARAB_CURRENCY.contains(instructions.get(0).getCurrency())); // only Arab currency settled on Sunday
		
		// settlement date on Monday
		instructions = reportGeneratorDao.findInstructionsBySettlementDate(toCalendar("2018-09-03"));
		assertEquals(4, instructions.size());
		
		// settlement date on Friday
		instructions = reportGeneratorDao.findInstructionsBySettlementDate(toCalendar("2018-09-07"));
		assertEquals(4, instructions.size());
		assertEquals(0, instructions.stream().filter(instruction -> Constants.ARAB_CURRENCY.contains(instruction.getCurrency()))
			.collect(Collectors.toList()).size()); // no Arab currency settled on Friday
		
		// settlement date on Saturday
		instructions = reportGeneratorDao.findInstructionsBySettlementDate(toCalendar("2018-09-08"));
		assertEquals(0, instructions.size()); // No settlements on Saturday
	}

	@Test
	public void testSaveReport() throws Exception {
		Report report = new Report();
		Calendar settlementDate = toCalendar("2018-09-06");
		reportGeneratorDao.saveReport(settlementDate, report);
	}

	@Test
	public void testFindReportBySettlementDate() throws Exception{
		Report expectedReport = new Report();
		Calendar settlementDate = toCalendar("2018-09-06");
		reportGeneratorDao.saveReport(settlementDate, expectedReport);
		Report actualReport = reportGeneratorDao.findReportBySettlementDate(settlementDate);
		assertEquals(expectedReport, actualReport);
	}

}
