package constants;

public enum Direction {
	
	BUY("B"), SELL("S");

	private String direction;

	private Direction(String direction) {
		this.direction = direction;
	}

	public String getValue() {
		return this.direction;
	}
}
