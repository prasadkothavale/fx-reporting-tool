package service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import domain.DailySettlementReport;
import domain.Instruction;

public class FileOperationsTest {
	
	@Test
	public void constructor() {
		new FileOperations();
	}
	
	@Test
	public void testReadData() throws Exception{
		File file = new File(FileOperationsTest.class.getResource("/TestData.csv").toURI());
		List<Instruction> readData = FileOperations.readData(file);
		assertEquals(29, readData.size());
		assertEquals(Calendar.MONDAY, readData.get(0).getSettlementDate().get(Calendar.DAY_OF_WEEK));
		assertEquals(Calendar.SUNDAY, readData.get(8).getSettlementDate().get(Calendar.DAY_OF_WEEK));
		assertEquals(Calendar.MONDAY, readData.get(11).getSettlementDate().get(Calendar.DAY_OF_WEEK));
		assertEquals(Calendar.SUNDAY, readData.get(24).getSettlementDate().get(Calendar.DAY_OF_WEEK));
		
		// negative tests
		file = new File(FileOperationsTest.class.getResource("/TestData2.csv").toURI());
		FileOperations.readData(file);
		file = new File("TestData3");
		FileOperations.readData(file);
	}
	
	@Test
	public void testPrintReport() throws Exception {
		File file = new File(FileOperationsTest.class.getResource("/TestData.csv").toURI());
		List<Instruction> instructions = FileOperations.readData(file);
		DailySettlementReport report = ReportGenerator.generateDailySettlementReport(instructions);
		ReportGenerator.generateRank(instructions);
		FileOperations.printReport(instructions, report);
	}

}
