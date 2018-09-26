package domain;

import java.util.Calendar;

import constants.Direction;

public class Instruction {

	private String entity;
	private Direction direction;
	private float agreedFx;
	private String currency;
	private Calendar instructionDate;
	private Calendar settlementDate;
	private int units;
	private float pricePerUnit;
	private float usdEquivalent;
	private int rank;
	
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public Direction getDirection() {
		return direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public float getAgreedFx() {
		return agreedFx;
	}
	
	public void setAgreedFx(float agreedFx) {
		this.agreedFx = agreedFx;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public Calendar getInstructionDate() {
		return instructionDate;
	}
	public void setInstructionDate(Calendar instructionDate) {
		this.instructionDate = instructionDate;
	}
	
	public Calendar getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(Calendar settlementDate) {
		this.settlementDate = settlementDate;
	}
	
	public int getUnits() {
		return units;
	}
	public void setUnits(int units) {
		this.units = units;
	}
	
	public float getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(float pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
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
	
	public Instruction() {}
	public Instruction(String entity, Direction direction, float agreedFx, String currency, Calendar instructionDate,
			Calendar settlementDate, int units, float pricePerUnit) {
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
