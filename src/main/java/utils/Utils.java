package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import constants.Constants;
import constants.Direction;
import domain.Instruction;
import domain.Report;
import exceptions.FxReportException;
import service.ReportGeneratorService;

public class Utils {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);

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
    public static LocalDate adjustSettlementDate(LocalDate settlementDate, String currency) {
        DayOfWeek dayOfWeek = settlementDate.getDayOfWeek();
        switch (dayOfWeek) {
            case FRIDAY:
                if (Constants.ARAB_CURRENCY.contains(currency)) {
                    settlementDate = settlementDate.plusDays(2);
                }
                break;
            case SATURDAY:
                if (Constants.ARAB_CURRENCY.contains(currency)) {
                    settlementDate = settlementDate.plusDays(1);
                } else {
                    settlementDate = settlementDate.plusDays(2);
                }
                break;
            case SUNDAY:
                if (!Constants.ARAB_CURRENCY.contains(currency)) {
                    settlementDate = settlementDate.plusDays(1);
                }
                break;
            default:
        }

        return settlementDate;
    }

    /**
     * Prints the {@link Report} in console
     *
     * @param report
     */
    public static void printReport(Report report, LocalDate settlementDate) {
        System.out.println(String.format("Settlement Report for %s day (%s)", settlementDate,  settlementDate.getDayOfWeek()));
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
        for (Instruction i : instructions) {
            System.out.println(String.format("%d\t| %s\t| %s\t| %.2f\t\t| %s\t\t| %s\t| %s\t| %d\t| %.2f\t| %.2f",
                    i.getRank(), i.getEntity(), i.getDirection(), i.getAgreedFx(), i.getCurrency(), i.getInstructionDate(),
                    i.getSettlementDate(), i.getUnits(), i.getPricePerUnit(), i.getUsdEquivalent()));
        }

    }

    /**
     * Temporary method to create  test data
     * @param reportGeneratorService
     */
    public static void createData(ReportGeneratorService reportGeneratorService) {
        //Friday
        Instruction instruction = new Instruction("Instruction1", Direction.BUY, 0.27f, "AED", LocalDate.parse("2018-08-30"), LocalDate.parse("2018-08-31"), 100, 120.20f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction2", Direction.SELL, 0.27f, "AED", LocalDate.parse("2018-08-30"), LocalDate.parse("2018-08-31"), 100, 119.48f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction3", Direction.BUY, 0.014f, "INR", LocalDate.parse("2018-08-30"), LocalDate.parse("2018-08-31"), 50, 140.40f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction4", Direction.SELL, 0.014f, "INR", LocalDate.parse("2018-08-30"), LocalDate.parse("2018-08-31"), 100, 150.48f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction5", Direction.BUY, 1.0f, "USD", LocalDate.parse("2018-08-30"), LocalDate.parse("2018-08-31"), 230, 140.40f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction6", Direction.SELL, 1.0f, "USD", LocalDate.parse("2018-08-30"), LocalDate.parse("2018-08-31"), 120, 150.48f);
        reportGeneratorService.saveInstruction(instruction);

        //Saturday
        instruction = new Instruction("Instruction7", Direction.BUY, 0.27f, "AED", LocalDate.parse("2018-09-01"), LocalDate.parse("2018-09-01"), 25, 120.20f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction8", Direction.SELL, 0.27f, "AED", LocalDate.parse("2018-09-01"), LocalDate.parse("2018-09-01"), 40, 119.48f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction9", Direction.BUY, 0.014f, "INR", LocalDate.parse("2018-09-01"), LocalDate.parse("2018-09-01"), 120, 140.40f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction10", Direction.SELL, 0.014f, "INR", LocalDate.parse("2018-09-01"), LocalDate.parse("2018-09-01"), 50, 150.48f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction11", Direction.BUY, 1.0f, "USD", LocalDate.parse("2018-09-01"), LocalDate.parse("2018-09-01"), 100, 140.40f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction12", Direction.SELL, 1.0f, "USD", LocalDate.parse("2018-09-01"), LocalDate.parse("2018-09-01"), 130, 150.48f);
        reportGeneratorService.saveInstruction(instruction);

        //Sunday
        instruction = new Instruction("Instruction13", Direction.BUY, 0.27f, "AED", LocalDate.parse("2018-09-02"), LocalDate.parse("2018-09-02"), 30, 120.20f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction14", Direction.SELL, 0.27f, "AED", LocalDate.parse("2018-09-02"), LocalDate.parse("2018-09-02"), 40, 119.48f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction15", Direction.BUY, 0.014f, "INR", LocalDate.parse("2018-09-02"), LocalDate.parse("2018-09-02"), 100, 140.40f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction16", Direction.SELL, 0.014f, "INR", LocalDate.parse("2018-09-02"), LocalDate.parse("2018-09-02"), 90, 150.48f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction17", Direction.BUY, 1.0f, "USD", LocalDate.parse("2018-09-02"), LocalDate.parse("2018-09-02"), 120, 140.40f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction18", Direction.SELL, 1.0f, "USD", LocalDate.parse("2018-09-02"), LocalDate.parse("2018-09-02"), 125, 150.48f);
        reportGeneratorService.saveInstruction(instruction);

        //Monday
        instruction = new Instruction("Instruction19", Direction.BUY, 0.27f, "AED", LocalDate.parse("2018-09-03"), LocalDate.parse("2018-09-03"), 4000, 120.20f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction20", Direction.SELL, 0.27f, "AED", LocalDate.parse("2018-09-03"), LocalDate.parse("2018-09-03"), 1200, 119.48f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction21", Direction.BUY, 0.014f, "INR", LocalDate.parse("2018-09-03"), LocalDate.parse("2018-09-03"), 50, 140.40f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction22", Direction.SELL, 0.014f, "INR", LocalDate.parse("2018-09-03"), LocalDate.parse("2018-09-03"), 120, 150.48f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction23", Direction.BUY, 1.0f, "USD", LocalDate.parse("2018-09-03"), LocalDate.parse("2018-09-03"), 125, 140.40f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction24", Direction.SELL, 1.0f, "USD", LocalDate.parse("2018-09-03"), LocalDate.parse("2018-09-03"), 110, 150.48f);
        reportGeneratorService.saveInstruction(instruction);

        //Tuesday
        instruction = new Instruction("Instruction25", Direction.BUY, 0.27f, "AED", LocalDate.parse("2018-09-04"), LocalDate.parse("2018-09-04"), 135, 120.20f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction26", Direction.SELL, 0.27f, "AED", LocalDate.parse("2018-09-04"), LocalDate.parse("2018-09-04"), 134, 119.48f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction27", Direction.BUY, 0.014f, "INR", LocalDate.parse("2018-09-04"), LocalDate.parse("2018-09-04"), 75, 140.40f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction28", Direction.SELL, 0.014f, "INR", LocalDate.parse("2018-09-04"), LocalDate.parse("2018-09-04"), 89, 150.48f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction29", Direction.BUY, 1.0f, "USD", LocalDate.parse("2018-09-04"), LocalDate.parse("2018-09-04"), 190, 140.40f);
        reportGeneratorService.saveInstruction(instruction);
        instruction = new Instruction("Instruction30", Direction.SELL, 1.0f, "USD", LocalDate.parse("2018-09-04"), LocalDate.parse("2018-09-04"), 140, 150.48f);
        reportGeneratorService.saveInstruction(instruction);
    }
}
