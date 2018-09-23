package domain;

import java.util.Calendar;
import java.util.Map;

public class DailySettlementReport {

	private Map<Calendar, Float> incomingReport;
	
	private Map<Calendar, Float> outgoingReport;

	public Map<Calendar, Float> getIncomingReport() {
		return incomingReport;
	}

	public void setIncomingReport(Map<Calendar, Float> incomingReport) {
		this.incomingReport = incomingReport;
	}

	public Map<Calendar, Float> getOutgoingReport() {
		return outgoingReport;
	}

	public void setOutgoingReport(Map<Calendar, Float> outgoingReport) {
		this.outgoingReport = outgoingReport;
	}
}
