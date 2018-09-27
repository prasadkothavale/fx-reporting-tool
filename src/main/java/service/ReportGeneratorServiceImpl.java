package service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import constants.Direction;
import dao.ReportGeneratorDao;
import dao.ReportGeneratorDaoImpl;
import domain.Instruction;
import domain.Report;
import exceptions.FxReportException;

import static utils.Utils.adjustSettlementDate;

public class ReportGeneratorServiceImpl implements ReportGeneratorService {

    private static ReportGeneratorServiceImpl reportGeneratorServiceImpl;

    private ReportGeneratorDao reportGeneratorDao;

    public ReportGeneratorServiceImpl(ReportGeneratorDao reportGeneratorDao) {
        this.reportGeneratorDao = reportGeneratorDao;
    }

    @Override
    public void saveInstruction(Instruction instruction) {
        if (null == instruction) {
            throw new FxReportException("Calling save instruction with null");
        }
        instruction.setSettlementDate(adjustSettlementDate(instruction.getSettlementDate(), instruction.getCurrency()));
        reportGeneratorDao.saveInstruction(instruction);
    }

    /* (non-Javadoc)
         * @see service.ReportGeneratorService#generateReport(java.util.Calendar)
         */
    @Override
    public Report generateReport(LocalDate settlementDate) {
        Report report = new Report();
        List<Instruction> instructions = reportGeneratorDao.findInstructionsBySettlementDate(settlementDate);

        // Calculate USD equivalent and separate out incoming and outgoing instructions
        instructions.stream().forEach(instruction -> {
            instruction.setUsdEquivalent(instruction.getAgreedFx() * instruction.getPricePerUnit() * instruction.getUnits());
            if (Direction.BUY.equals(instruction.getDirection())) {
                report.setOutgoingUSD(report.getOutgoingUSD() + instruction.getUsdEquivalent());
                report.getRankedOutgoingInstructions().add(instruction);
            } else {
                report.setIncomingUSD(report.getIncomingUSD() + instruction.getUsdEquivalent());
                report.getRankedIncomingInstructions().add(instruction);
            }
        });

        // Sort instructions based in USD equivalent and assign rank
        generateRank(report.getRankedIncomingInstructions());
        generateRank(report.getRankedOutgoingInstructions());

        return report;
    }

    /**
     * Sort instruction based on USD equivalent value to get rank
     *
     * @param instructions
     */
    private static void generateRank(List<Instruction> instructions) {
        instructions.sort(comparator);
        for (int rank = 0; rank < instructions.size(); rank++) {
            instructions.get(rank).setRank(rank + 1);
        }
    }

    public void setReportGeneratorDao(ReportGeneratorDao reportGeneratorDao) {
        this.reportGeneratorDao = reportGeneratorDao;
    }

    static Comparator<? super Instruction> comparator = new Comparator<Instruction>() {
        @Override
        public int compare(Instruction o1, Instruction o2) {
            return -1 * (new Float(o1.getUsdEquivalent()).compareTo(o2.getUsdEquivalent()));
        }
    };

}
