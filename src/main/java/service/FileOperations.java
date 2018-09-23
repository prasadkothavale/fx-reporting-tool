package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import config.Constants;
import domain.DailySettlementReport;
import domain.Instruction;

public class FileOperations {

	/**
	 * Reads given file and maps it in {@link Instruction} list
	 * 
	 * @param file
	 * @return
	 */
	public static List<Instruction> readData(File file) {
		final List<Instruction> instructions = new ArrayList<>();
		try {
			final BufferedReader br = new BufferedReader(new FileReader(file));
			br.readLine(); // Skip headers
			String line = br.readLine();
			while (line != null && !"".equals(line.trim())) {
				try {
					instructions.add(getInstruction(line));
				} catch (Exception e) {
					// skip single record in case of parsing error
					System.err.println(String.format("%s error while reading line\n%s", e.getMessage(), line));
				}
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return instructions;
	}

	/**
	 * Map {@link String} line to {@link Instruction}.
	 * 
	 * @param line
	 * @return
	 * @throws ParseException
	 */
	private static Instruction getInstruction(String line) throws ParseException {
		final String[] columns = line.split(Constants.CSV_SEPARATOR);
		final Instruction instruction = new Instruction();
		instruction.setEntity(columns[0]);
		instruction.setDirection(columns[1]);
		instruction.setAgreedFx(Float.parseFloat(columns[2]));
		instruction.setCurrency(columns[3]);
		instruction.setUnits(Integer.parseInt(columns[6]));
		instruction.setPricePerUnit(Float.parseFloat(columns[7]));

		final Calendar instructionDate = Calendar.getInstance();
		instructionDate.setTime(new SimpleDateFormat(Constants.DATE_FORMAT).parse(columns[4]));
		instruction.setInstructionDate(instructionDate);
		final Calendar settlementDate = Calendar.getInstance();
		settlementDate.setTime(new SimpleDateFormat(Constants.DATE_FORMAT).parse(columns[5]));
		instruction.setSettlementDate(adjustSettlementDate(settlementDate, instruction.getCurrency()));

		// USD amount of a trade = Price per unit * Units * Agreed Fx
		instruction
				.setUsdEquivalent(instruction.getAgreedFx() * instruction.getPricePerUnit() * instruction.getUnits());

		return instruction;
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
	private static Calendar adjustSettlementDate(Calendar settlementDate, String currency) {
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
	 * Prints the report in console
	 * 
	 * @param instructions
	 * @param report
	 */
	public static void printReport(List<Instruction> instructions, DailySettlementReport report) {
		System.out.println("Daily Incoming Settlements");
		System.out.println("==========================\n");
		System.out.println("Date\t\t| Amount(USD)");
		System.out.println("---------------------------");
		printReport(report.getIncomingReport());
		System.out.println("---------------------------");
		
		System.out.println("\n\nDaily Outgoing Settlements");
		System.out.println("==========================\n");
		System.out.println("Date\t\t| Amount(USD)");
		System.out.println("---------------------------");
		printReport(report.getOutgoingReport());
		System.out.println("---------------------------");
		
		System.out.println("\n\nIncoming Instructions By Ranking");
		System.out.println("================================\n");
		System.out.println("Rank\t| Entity\t| B/S\t| Agreed Fx\t| Currency\t| Inst. Date\t| Sttl. Date\t| Units\t| Price/Unit\t| USD Eq.");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
		printReport(instructions, "S");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
		
		System.out.println("\n\nOutgoing Instructions By Ranking");
		System.out.println("================================\n");
		System.out.println("Rank\t| Entity\t| B/S\t| Agreed Fx\t| Currency\t| Inst. Date\t| Sttl. Date\t| Units\t| Price/Unit\t| USD Eq.");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
		printReport(instructions, "B");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
	}

	private static void printReport(List<Instruction> instructions, String direction) {
		final SimpleDateFormat f = new SimpleDateFormat(Constants.DATE_FORMAT);
		for(Instruction i : instructions) {
			if(direction.equalsIgnoreCase(i.getDirection())) {
				System.out.println(String.format("%d\t| %s\t| %s\t| %.2f\t\t| %s\t\t| %s\t| %s\t| %d\t| %.2f\t| %.2f", 
						i.getRank(), i.getEntity(), i.getDirection(), i.getAgreedFx(), i.getCurrency(), f.format(i.getInstructionDate().getTime()),
						f.format(i.getSettlementDate().getTime()), i.getUnits(), i.getPricePerUnit(), i.getUsdEquivalent()));
			}
		}
		
	}

	private static void printReport(Map<Calendar, Float> report) {
		final SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
		for (Calendar settlementDate : report.keySet()) {
			System.out.println(String.format("%s\t| %s", format.format(settlementDate.getTime()), report.get(settlementDate)));
		}
		
	}
}
