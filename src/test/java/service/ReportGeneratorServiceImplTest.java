package service;

import dao.ReportGeneratorDao;
import dao.ReportGeneratorDaoImpl;
import domain.Report;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static utils.Utils.createData;

public class ReportGeneratorServiceImplTest {

    private ReportGeneratorDao reportGeneratorDao;
    private ReportGeneratorService reportGeneratorService;

    @Before
    public void setUp() throws Exception {
        reportGeneratorDao = new ReportGeneratorDaoImpl();
        reportGeneratorService = new ReportGeneratorServiceImpl(reportGeneratorDao);
        createData(reportGeneratorService);
    }

    @Test
    public void testGenerateReport_Friday() throws Exception {
        LocalDate settlementDate = LocalDate.parse("2018-08-31");
        Report report = reportGeneratorService.generateReport(settlementDate);
        assertNotNull(report);
        assertEquals("Incoming", 18268.271f, report.getIncomingUSD());
        assertEquals("Outgoing", 32390.277f, report.getOutgoingUSD());
        assertEquals("Incoming Ranked Entity", "Instruction6", report.getRankedIncomingInstructions().get(0).getEntity());
        assertEquals("Outgoing Ranked Entity", "Instruction5", report.getRankedOutgoingInstructions().get(0).getEntity());
    }

    @Test
    public void testGenerateReport_Saturday() throws Exception {
        LocalDate settlementDate = LocalDate.parse("2018-09-01");
        Report report = reportGeneratorService.generateReport(settlementDate);
        assertNotNull(report);
        assertEquals("Incoming", 0.0f, report.getIncomingUSD());
        assertEquals("Outgoing", 0.0f, report.getOutgoingUSD());
        assertEquals("Incoming Ranked Entity", 0, report.getRankedIncomingInstructions().size());
        assertEquals("Outgoing Ranked Entity", 0, report.getRankedIncomingInstructions().size());
    }

    @Test
    public void testGenerateReport_Sunday() throws Exception {
        LocalDate settlementDate = LocalDate.parse("2018-09-02");
        Report report = reportGeneratorService.generateReport(settlementDate);
        assertNotNull(report);
        assertEquals("Incoming", 5806.7285f, report.getIncomingUSD());
        assertEquals("Outgoing", 5030.37f, report.getOutgoingUSD());
    }

    @Test
    public void testGenerateReport_Monday() throws Exception {
        LocalDate settlementDate = LocalDate.parse("2018-09-03");
        Report report = reportGeneratorService.generateReport(settlementDate);
        assertNotNull(report);
        assertEquals("Incoming", 94184.47f, report.getIncomingUSD());
        assertEquals("Outgoing", 178784.7f, report.getOutgoingUSD());
        assertEquals("Incoming Ranked Entity", "Instruction20", report.getRankedIncomingInstructions().get(0).getEntity());
        assertEquals("Outgoing Ranked Entity", "Instruction19", report.getRankedOutgoingInstructions().get(0).getEntity());
    }

    @Test
    public void testGenerateReport_Tuesday() throws Exception {
        LocalDate settlementDate = LocalDate.parse("2018-09-04");
        Report report = reportGeneratorService.generateReport(settlementDate);
        assertNotNull(report);
        assertEquals("Incoming", 25577.484f, report.getIncomingUSD());
        assertEquals("Outgoing", 31204.707f, report.getOutgoingUSD());
        assertEquals("Incoming Ranked Entity", "Instruction30", report.getRankedIncomingInstructions().get(0).getEntity());
        assertEquals("Outgoing Ranked Entity", "Instruction29", report.getRankedOutgoingInstructions().get(0).getEntity());
    }
}
