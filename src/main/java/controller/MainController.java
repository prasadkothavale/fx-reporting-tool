package controller;

import constants.Direction;
import dao.ReportGeneratorDao;
import dao.ReportGeneratorDaoImpl;
import domain.Instruction;
import domain.Report;
import exceptions.FxReportException;
import service.ReportGeneratorService;
import service.ReportGeneratorServiceImpl;

import java.time.LocalDate;

import static utils.Utils.createData;
import static utils.Utils.printReport;

public class MainController {

    public static void main(String[] args) throws FxReportException {
        //In real app, will be injected by Spring
        ReportGeneratorDao reportGeneratorDao = new ReportGeneratorDaoImpl();
        ReportGeneratorService reportGeneratorService = new ReportGeneratorServiceImpl(reportGeneratorDao);
        createData(reportGeneratorService);
        LocalDate settlementDate = LocalDate.parse("2018-08-30");

        for (int i=0; i< 5; i++) {
            LocalDate displayDate = settlementDate.plusDays(i);
            Report report = reportGeneratorService.generateReport(displayDate);
            printReport(report, displayDate);
        }

    }

}
