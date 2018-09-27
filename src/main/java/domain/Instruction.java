package domain;

import java.time.LocalDate;
import java.util.Calendar;

import constants.Direction;

public class Instruction {
    private String entity;
    private Direction direction;
    private float agreedFx;
    private String currency;
    private LocalDate instructionDate;
    private LocalDate settlementDate;
    private int units;
    private float pricePerUnit;
    private float usdEquivalent;
    private int rank;

    public String getEntity() {
        return entity;
    }

    public Direction getDirection() {
        return direction;
    }

    public float getAgreedFx() {
        return agreedFx;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getInstructionDate() {
        return instructionDate;
    }

    public LocalDate getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(LocalDate settlementDate) {
        this.settlementDate = settlementDate;
    }

    public int getUnits() {
        return units;
    }

    public float getPricePerUnit() {
        return pricePerUnit;
    }

    public float getUsdEquivalent() {
        return usdEquivalent;
    }

    public void setUsdEquivalent(float usdEquivalent) {
        this.usdEquivalent = usdEquivalent;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Instruction(String entity, Direction direction, float agreedFx, String currency, LocalDate instructionDate,
                       LocalDate settlementDate, int units, float pricePerUnit) {
        this.entity = entity;
        this.direction = direction;
        this.agreedFx = agreedFx;
        this.currency = currency;
        this.instructionDate = instructionDate;
        this.settlementDate = settlementDate;
        this.units = units;
        this.pricePerUnit = pricePerUnit;
    }


}
