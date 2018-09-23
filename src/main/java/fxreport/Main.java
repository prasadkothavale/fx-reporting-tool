package fxreport;

import java.io.File;
import java.util.List;

import domain.DailySettlementReport;
import domain.Instruction;
import service.FileOperations;
import service.ReportGenerator;

public class Main {

	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.err.println("Please specify input file");
		} else {
			List<Instruction> instructions = FileOperations.readData(new File(args[0]));
			DailySettlementReport dailySettlementReport = ReportGenerator.generateDailySettlementReport(instructions);
			ReportGenerator.generateRank(instructions);
			FileOperations.printReport(instructions, dailySettlementReport);
		}
	}

}
