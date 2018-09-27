package domain;

import java.util.ArrayList;
import java.util.List;

public class Report {

	private float incomingUSD;
	
	private float outgoingUSD;
	
	private List<Instruction> rankedIncomingInstructions;
	
	private List<Instruction> rankedOutgoingInstructions;

	public Report() {
		incomingUSD = 0f;
		outgoingUSD = 0f;
		rankedIncomingInstructions = new ArrayList<>();
		rankedOutgoingInstructions = new ArrayList<>();
	}
	
	public float getIncomingUSD() {
		return incomingUSD;
	}

	public void setIncomingUSD(float incomingUSD) {
		this.incomingUSD = incomingUSD;
	}

	public float getOutgoingUSD() {
		return outgoingUSD;
	}

	public void setOutgoingUSD(float outgoingUSD) {
		this.outgoingUSD = outgoingUSD;
	}

	public List<Instruction> getRankedIncomingInstructions() {
		return rankedIncomingInstructions;
	}

	/*public void setRankedIncomingInstructions(List<Instruction> rankedIncomingInstructions) {
		this.rankedIncomingInstructions = rankedIncomingInstructions;
	}*/

	public List<Instruction> getRankedOutgoingInstructions() {
		return rankedOutgoingInstructions;
	}

	/*public void setRankedOutgoingInstructions(List<Instruction> rankedOutgoingInstructions) {
		this.rankedOutgoingInstructions = rankedOutgoingInstructions;
	}*/

}
