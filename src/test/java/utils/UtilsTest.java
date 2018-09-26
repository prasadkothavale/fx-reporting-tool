package utils;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

import constants.Direction;
import domain.Instruction;
import domain.Report;
import exceptions.FxReportException;

public class UtilsTest {

	@Test
	public void testToCalendar() throws FxReportException {
		String date = "2018-09-06";
		Calendar calendar = Utils.toCalendar(date);
		assertEquals(date, Utils.getDateAsString(calendar));
	}
	
	@Test(expected = FxReportException.class )
	public void testToCalendarException() throws FxReportException {
		String date = "abcd";
		Utils.toCalendar(date);
	}

	@Test
	public void testAdjustSettlementDate() throws FxReportException {
		Calendar actualDate = Utils.adjustSettlementDate(Utils.toCalendar("2018-09-01"), "AED");
		assertEquals(Utils.toCalendar("2018-09-02"), actualDate);
		
		actualDate = Utils.adjustSettlementDate(Utils.toCalendar("2018-09-02"), "AED");
		assertEquals(Utils.toCalendar("2018-09-02"), actualDate);
		
		actualDate = Utils.adjustSettlementDate(Utils.toCalendar("2018-09-07"), "AED");
		assertEquals(Utils.toCalendar("2018-09-09"), actualDate);
		
		actualDate = Utils.adjustSettlementDate(Utils.toCalendar("2018-09-10"), "AED");
		assertEquals(Utils.toCalendar("2018-09-10"), actualDate);
		
		actualDate = Utils.adjustSettlementDate(Utils.toCalendar("2018-09-01"), "INR");
		assertEquals(Utils.toCalendar("2018-09-03"), actualDate);
		
		actualDate = Utils.adjustSettlementDate(Utils.toCalendar("2018-09-02"), "INR");
		assertEquals(Utils.toCalendar("2018-09-03"), actualDate);
		
		actualDate = Utils.adjustSettlementDate(Utils.toCalendar("2018-09-07"), "INR");
		assertEquals(Utils.toCalendar("2018-09-07"), actualDate);
		
		actualDate = Utils.adjustSettlementDate(Utils.toCalendar("2018-09-10"), "INR");
		assertEquals(Utils.toCalendar("2018-09-10"), actualDate);
	}

	@Test
	public void testPrintReport() throws Exception{
		Report report = new Report();
		report.getRankedOutgoingInstructions().add(new Instruction("EO487I", Direction.BUY, 1.17f, "EUR", Utils.toCalendar("2018-08-31"), Utils.toCalendar("2018-09-01"), 370, 119.48f));
		report.getRankedIncomingInstructions().add(new Instruction("EO487I", Direction.SELL, 1.17f, "EUR", Utils.toCalendar("2018-08-31"), Utils.toCalendar("2018-09-01"), 370, 119.48f));
		Utils.printReport(report, Utils.toCalendar("2018-09-01"));
		
	}

}
