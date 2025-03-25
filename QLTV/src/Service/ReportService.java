package Service;

import DAO.ReportDAO;
import Model.Report;
import java.util.HashMap;

public class ReportService implements Service<Report> {
    private final ReportDAO reportDAO = new ReportDAO();
    private HashMap<Report, Report> reportMap = new HashMap<>();

    public ReportService() {
        reportMap = reportDAO.getAll();
    }

    @Override
    public Report findById(String id) {
        Report reportFind = reportDAO.getById(id);
        if (reportFind == null) {
            System.out.println("Invalid id. ");
        }
        return reportFind;
    }

    @Override
    public void insert(Report report) {
        reportDAO.insert(report);
        reportMap.put(new Report(report.getCustomerId(), report.getBookId()), report);
    }

    @Override
    public void delete(String id) {
        reportDAO.delete(id);
        reportMap.remove(new Report(id, null));
    }

    @Override
    public void display(Report report) {
        System.out.println(report);
    }

    public void update(Report report) {
        reportDAO.update(report);
        reportMap.put(new Report(report.getCustomerId(), report.getBookId()), report);
    }

    public HashMap<Report, Report> getReportMap() {
        reportMap.clear();
         reportMap = reportDAO.getAll();
        return reportMap;
    }
}
