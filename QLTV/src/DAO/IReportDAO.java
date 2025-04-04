package DAO;

import Model.Report;
import java.util.HashMap;

public interface IReportDAO extends GenericDAO<Report, HashMap<Report, Report>> {

    @Override
    void delete(String id);

    @Override
    void update(Report entity);

    @Override
    void insert(Report entity);

    @Override
    Report getById(String id);

    @Override
    HashMap<Report, Report> getAll();
}
