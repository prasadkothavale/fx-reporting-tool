package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import constants.Constants;
import domain.Instruction;
import domain.Report;
import exceptions.FxReportException;

public class Utils {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	/**
	 * Converts given {@link String} to {@link Calendar}
	 * 
	 * @param dateString
	 * @return
	 * @throws FxReportException
	 */
	public static Calendar toCalendar(String dateString) throws FxReportException {
		try {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateFormat.parse(dateString));
			return calendar;
		} catch (ParseException e) {
			throw new FxReportException(String.format("Invalid date %s", dateString), e);
		}
	}
	
	/**
	 * Converts given {@link Calendar} to {@link String}
	 * @param calendar
	 * @return
	 */
	public static String getDateAsString(Calendar calendar) {
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * A work week starts Monday and ends Friday, unless the currency of the trade
	 * is AED or SAR, where the work week starts Sunday and ends Thursday.No other
	 * holidays to be taken into account. A trade can only be settled on a working
	 * day
	 * 
	 * @param settlementDate
	 * @param currency
	 * @return
	 */
	public static Calendar adjustSettlementDate(Calendar settlementDate, String currency) {
		int dayOfWeek = settlementDate.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case Calendar.FRIDAY:
			if (Constants.ARAB_CURRENCY.contains(currency)) {
				settlementDate.add(Calendar.DATE, 2);
			}
			break;
		case Calendar.SATURDAY:
			if (Constants.ARAB_CURRENCY.contains(currency)) {
				settlementDate.add(Calendar.DATE, 1);
			} else {
				settlementDate.add(Calendar.DATE, 2);
			}
			break;
		case Calendar.SUNDAY:
			if (!Constants.ARAB_CURRENCY.contains(currency)) {
				settlementDate.add(Calendar.DATE, 1);
			}
			break;
		default:
		}

		return settlementDate;
	}
	
	/**
	 * Prints the {@link Report} in console
	 * @param report
	 */
	public static void printReport(Report report, Calendar settlementDate) {
		System.out.println(String.format("Settlement Report for %s", getDateAsString(settlementDate)));
		System.out.println("================================\n");
		System.out.println(String.format("Settled incoming amount: %s USD", report.getIncomingUSD()));
		System.out.println(String.format("Settled outgoing amount: %s USD\n", report.getOutgoingUSD()));
		
		System.out.println("\nIncoming Instructions By Ranking");
		System.out.println("================================\n");
		System.out.println("Rank\t| Entity\t| B/S\t| Agreed Fx\t| Currency\t| Inst. Date\t| Sttl. Date\t| Units\t| Price/Unit\t| USD Eq.");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
		printReport(report.getRankedIncomingInstructions());
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
		
		System.out.println("\n\nOutgoing Instructions By Ranking");
		System.out.println("================================\n");
		System.out.println("Rank\t| Entity\t| B/S\t| Agreed Fx\t| Currency\t| Inst. Date\t| Sttl. Date\t| Units\t| Price/Unit\t| USD Eq.");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
		printReport(report.getRankedOutgoingInstructions());
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
	}
	
	private static void printReport(List<Instruction> instructions) {
		for(Instruction i : instructions) {
			System.out.println(String.format("%d\t| %s\t| %s\t| %.2f\t\t| %s\t\t| %s\t| %s\t| %d\t| %.2f\t| %.2f", 
					i.getRank(), i.getEntity(), i.getDirection(), i.getAgreedFx(), i.getCurrency(),getDateAsString(i.getInstructionDate()),
					getDateAsString(i.getSettlementDate()), i.getUnits(), i.getPricePerUnit(), i.getUsdEquivalent()));
		}
		
	}
}
