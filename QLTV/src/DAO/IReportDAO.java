/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Report;
import java.util.TreeSet;

/**
 *
 * @author NHAT NAM
 */
public interface IReportDAO extends GenericDAO<Report, TreeSet<Report>>{

    @Override
    public void delete(String id);

    @Override
    public void update(Report entity);

    @Override
    public void insert(Report entity);

    @Override
    public Report getById(String id);

    @Override
    public TreeSet<Report> getAll();
    
}
