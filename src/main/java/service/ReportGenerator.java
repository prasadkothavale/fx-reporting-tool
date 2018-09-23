package service;

import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import domain.DailySettlementReport;
import domain.Instruction;

public class ReportGenerator {

	/**
	 * Generate daily settlement report for amount in USD incoming and outgoing everyday
	 * 
	 * @param instructions
	 * @return
	 */
	public static DailySettlementReport generateDailySettlementReport(List<Instruction> instructions) {
		final DailySettlementReport report = new DailySettlementReport();
		final Map<Calendar, Float> incomingReport = new HashMap<>();
		final Map<Calendar, Float> outgoingReport = new HashMap<>();
		for (Instruction instruction : instructions) {
			if ("B".equalsIgnoreCase(instruction.getDirection())) {
				insertOrUpdateRecord(instruction, outgoingReport);
			} else {
				insertOrUpdateRecord(instruction, incomingReport);
			}
		}
		
		// sorted map
		report.setIncomingReport(new TreeMap<>(incomingReport));
		report.setOutgoingReport(new TreeMap<>(outgoingReport));
		return report;
	}

	/**
	 * If settlement date is present in report update the record else insert new record.
	 * 
	 * @param instruction
	 * @param report
	 */
	private static void insertOrUpdateRecord(Instruction instruction, Map<Calendar, Float> report) {
		final Calendar settlementDate = instruction.getSettlementDate();
		final float usdEquivalent = instruction.getUsdEquivalent();
		if (report.containsKey(settlementDate)) {
			report.put(settlementDate, report.get(settlementDate)+usdEquivalent);
		} else {
			report.put(settlementDate, usdEquivalent);
		}
		
	}
	
	/**
	 * Sort instruction based on USD equivalent value to get rank
	 * 
	 * @param instructions
	 */
	public static void generateRank(List<Instruction> instructions) {
		Comparator<? super Instruction> comparator = new Comparator<Instruction>() {

			@Override
			public int compare(Instruction o1, Instruction o2) {
				return -1*(new Float(o1.getUsdEquivalent()).compareTo(o2.getUsdEquivalent()));
			}
		};
		instructions.sort(comparator);
		
		int incomingRank = 1;
		int outgoingRank = 1;
		for (Instruction instruction : instructions) {
			if("B".equalsIgnoreCase(instruction.getDirection())) {
				instruction.setRank(outgoingRank++);
			} else {
				instruction.setRank(incomingRank++);
			}
		}
	}
}
