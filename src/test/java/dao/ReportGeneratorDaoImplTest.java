package dao;

import constants.Constants;
import constants.Direction;
import domain.Instruction;
import domain.Report;
import exceptions.FxReportException;
import org.junit.Before;
import org.junit.Test;
import service.ReportGeneratorService;
import service.ReportGeneratorServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utils.Utils.createData;

public class ReportGeneratorDaoImplTest {

    private ReportGeneratorDaoImpl reportGeneratorDao;

    @Before
    public void setUp() throws Exception {
        reportGeneratorDao = new ReportGeneratorDaoImpl();
    }

    @Test
    public void testFindInstructionsBySettlementDate() {

        ReportGeneratorService reportGeneratorService = new ReportGeneratorServiceImpl(reportGeneratorDao);
        createData(reportGeneratorService);

        // settlement date on Friday
        List<Instruction> instructions = reportGeneratorDao.findInstructionsBySettlementDate(LocalDate.parse("2018-08-31"));
        assertEquals(4, instructions.size());
        assertEquals(0, instructions.stream().filter(instruction -> Constants.ARAB_CURRENCY.contains(instruction.getCurrency()))
                .collect(Collectors.toList()).size()); // no Arab currency settled on Friday

        // settlement date on Saturday
        instructions = reportGeneratorDao.findInstructionsBySettlementDate(LocalDate.parse("2018-09-01"));
        assertEquals(0, instructions.size()); // No settlements on Saturday

        // settlement date on Sunday
        instructions = reportGeneratorDao.findInstructionsBySettlementDate(LocalDate.parse("2018-09-02"));
        assertEquals(6, instructions.size());
        assertTrue(Constants.ARAB_CURRENCY.contains(instructions.get(0).getCurrency())); // only Arab currency settled on Sunday

        // settlement date on Monday (4 (INR/USD on Sat)  +  4 (INR/USD on Sunday) + 6 (AED/INR/USD on Monday)
        instructions = reportGeneratorDao.findInstructionsBySettlementDate(LocalDate.parse("2018-09-03"));
        assertEquals(14, instructions.size());
    }

    @Test(expected = FxReportException.class)
    public void testSaveInstruction_withNull() {
        Instruction instruction = new Instruction("Instruction1", Direction.BUY, 0.27f, "AED", LocalDate.parse("2018-08-30"), LocalDate.parse("2018-08-31"), 100, 120.20f);
        reportGeneratorDao.saveInstruction(null);
    }

    @Test(expected = FxReportException.class)
    public void testSaveInstruction() {
        Instruction instruction = new Instruction("Instruction1", Direction.BUY, 0.27f, "AED", LocalDate.parse("2018-08-30"), LocalDate.parse("2018-08-31"), 100, 120.20f);
        Instruction actual = reportGeneratorDao.saveInstruction(null);
        assertEquals("Saving Instruction", instruction, actual);
    }

}
