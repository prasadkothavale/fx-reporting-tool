package dao;

import static org.junit.Assert.assertEquals;
import static utils.Utils.toCalendar;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
		List<Instruction> instructions = reportGeneratorDao.findInstructionsBySettlementDate(toCalendar("2018-09-06"));
		assertEquals(5, instructions.size());
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
