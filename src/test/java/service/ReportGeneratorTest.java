package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import domain.DailySettlementReport;
import domain.Instruction;

public class ReportGeneratorTest {

	private List<Instruction> instructions;
	
	@Before
	public void init() throws Exception {
		new ReportGenerator();
		File file = new File(FileOperationsTest.class.getResource("/TestData.csv").toURI());
		instructions = FileOperations.readData(file);
	}
	
	@Test
	public void testGenerateDailySettlementReport() throws Exception{
		DailySettlementReport report = ReportGenerator.generateDailySettlementReport(instructions);
		assertEquals(11, report.getIncomingReport().size());
		assertEquals(10, report.getOutgoingReport().size());
	}

	@Test
	public void testGenerateRank() throws Exception{
		ReportGenerator.generateRank(instructions);
		float previousUsdEquivalent = Float.MAX_VALUE;
		for (Instruction instruction : instructions) {
			if (previousUsdEquivalent < instruction.getUsdEquivalent()) {
				fail(String.format("Sorting order failed as %s ranked heigher than %s", previousUsdEquivalent, instruction.getUsdEquivalent()));
			}
			previousUsdEquivalent = instruction.getUsdEquivalent();
		}
	}

}
