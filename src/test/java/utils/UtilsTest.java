package utils;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Calendar;

import org.junit.Test;

import constants.Direction;
import domain.Instruction;
import domain.Report;
import exceptions.FxReportException;

public class UtilsTest {

	@Test
	public void testAdjustSettlementDate() throws FxReportException {
		LocalDate actualDate = Utils.adjustSettlementDate(LocalDate.parse("2018-09-01"), "AED");
		assertEquals(LocalDate.parse("2018-09-02"), actualDate);
		
		actualDate = Utils.adjustSettlementDate(LocalDate.parse("2018-09-02"), "AED");
		assertEquals(LocalDate.parse("2018-09-02"), actualDate);
		
		actualDate = Utils.adjustSettlementDate(LocalDate.parse("2018-09-07"), "AED");
		assertEquals(LocalDate.parse("2018-09-09"), actualDate);
		
		actualDate = Utils.adjustSettlementDate(LocalDate.parse("2018-09-10"), "AED");
		assertEquals(LocalDate.parse("2018-09-10"), actualDate);
		
		actualDate = Utils.adjustSettlementDate(LocalDate.parse("2018-09-01"), "INR");
		assertEquals(LocalDate.parse("2018-09-03"), actualDate);
		
		actualDate = Utils.adjustSettlementDate(LocalDate.parse("2018-09-02"), "INR");
		assertEquals(LocalDate.parse("2018-09-03"), actualDate);
		
		actualDate = Utils.adjustSettlementDate(LocalDate.parse("2018-09-07"), "INR");
		assertEquals(LocalDate.parse("2018-09-07"), actualDate);
		
		actualDate = Utils.adjustSettlementDate(LocalDate.parse("2018-09-10"), "INR");
		assertEquals(LocalDate.parse("2018-09-10"), actualDate);
	}

	@Test
	public void testPrintReport() throws Exception{
		Report report = new Report();
		report.getRankedOutgoingInstructions().add(new Instruction("EO487I", Direction.BUY, 1.17f, "EUR", LocalDate.parse("2018-08-31"), LocalDate.parse("2018-09-01"), 370, 119.48f));
		report.getRankedIncomingInstructions().add(new Instruction("EO487I", Direction.SELL, 1.17f, "EUR", LocalDate.parse("2018-08-31"), LocalDate.parse("2018-09-01"), 370, 119.48f));
		Utils.printReport(report, LocalDate.parse("2018-09-01"));
		
	}

}
