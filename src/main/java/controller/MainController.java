package controller;

import static utils.Utils.toCalendar;
import static utils.Utils.printReport;

import java.util.Calendar;

import domain.Report;
import exceptions.FxReportException;
import service.ReportGeneratorService;
import service.ReportGeneratorServiceImpl;

public class MainController {
	
	public static void main(String[] args) throws FxReportException {
		if (args.length < 1) {
			throw new FxReportException("Please specify settlement date to generate report");
		} else {
			final Calendar settlementDate = toCalendar(args[0]);
			final ReportGeneratorService reportGeneratorService = ReportGeneratorServiceImpl.getInstance();
			reportGeneratorService.generateReport(settlementDate);
			final Report report = reportGeneratorService.findReportBySettlementDate(settlementDate);
			printReport(report, settlementDate);
		}
	}

}
