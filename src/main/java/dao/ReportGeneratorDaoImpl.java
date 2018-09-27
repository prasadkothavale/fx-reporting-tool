package dao;

import domain.Instruction;
import domain.Report;
import exceptions.FxReportException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static utils.Utils.adjustSettlementDate;

public class ReportGeneratorDaoImpl implements ReportGeneratorDao {

    private List<Instruction> instructions = new ArrayList<>();

    @Override
    public Instruction saveInstruction(Instruction instruction) {
        if (null == instruction) {
            throw new FxReportException("Calling save instruction with null");
        }
        instructions.add(instruction);
        return instruction;
    }

    /* (non-Javadoc)
         * @see dao.ReportGeneratorDao#findInstructionsBySettlementDate(java.util.Calendar)
         */
    @Override
    public List<Instruction> findInstructionsBySettlementDate(LocalDate settlementDate) {
        return instructions.stream().filter(instruction -> {
            return settlementDate.equals(instruction.getSettlementDate());
        }).collect(Collectors.toList());
    }

}
