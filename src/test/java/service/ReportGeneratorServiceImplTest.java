package service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static utils.Utils.toCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import constants.Direction;
import dao.ReportGeneratorDao;
import dao.ReportGeneratorDaoImpl;
import domain.Instruction;
import domain.Report;

public class ReportGeneratorServiceImplTest {

	private ReportGeneratorServiceImpl reportGeneratorServiceImpl;
	
	private ReportGeneratorDao reportGeneratorDaoMock;
	
	@Before
	public void setUp() throws Exception {
		reportGeneratorServiceImpl = ReportGeneratorServiceImpl.getInstance();
		reportGeneratorDaoMock = mock(ReportGeneratorDao.class);
		reportGeneratorServiceImpl.setReportGeneratorDao(reportGeneratorDaoMock);
	}

	@Test
	public void testGenerateReport() throws Exception {
		List<Instruction> instructions = new ArrayList<>();
		instructions.add(new Instruction("EO487I", Direction.BUY, 1.17f, "EUR", toCalendar("2018-08-31"), toCalendar("2018-09-02"), 370, 119.48f));
		instructions.add(new Instruction("OB527I", Direction.SELL, 0.014f, "INR", toCalendar("2018-09-01"), toCalendar("2018-09-02"), 597, 340.43f));
		instructions.add(new Instruction("YV787D", Direction.BUY, 0.27f, "AED", toCalendar("2018-09-01"), toCalendar("2018-09-02"), 600, 144.64f));
		instructions.add(new Instruction("DU783H", Direction.SELL, 0.27f, "SAR", toCalendar("2018-09-01"), toCalendar("2018-09-02"), 244, 457.46f));
		
		Report report = new Report();
		when(reportGeneratorDaoMock.findInstructionsBySettlementDate(any(Calendar.class))).thenReturn(instructions);
		when(reportGeneratorDaoMock.saveReport(any(Calendar.class), any(Report.class))).thenReturn(report);
		Report actualReport = reportGeneratorServiceImpl.generateReport(toCalendar("2018-09-06"));
		assertNotNull(actualReport);
	}

	@Test
	public void testFindReportBySettlementDate() {
		Report report = new Report();
		when(reportGeneratorDaoMock.findReportBySettlementDate(any(Calendar.class))).thenReturn(report);
		assertNotNull(report);
	}
	
	@After
	public void tearDown() throws Exception {
		reportGeneratorServiceImpl.setReportGeneratorDao(ReportGeneratorDaoImpl.getInstance());
	}

}
