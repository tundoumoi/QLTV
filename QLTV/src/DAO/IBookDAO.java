/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
<<<<<<< HEAD
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.awt.print.Book;
import java.util.TreeSet;

/**
 *
 * @author NHAT NAM
 */
public interface IBookDAO extends GenericDAO<BookDAO, TreeSet<Book>>{

    @Override
    public void delete(String id);

    @Override
    public void update(BookDAO entity);

    @Override
    public void insert(BookDAO entity);

    @Override
    public BookDAO getById(String id);

    @Override
    public TreeSet<Book> getAll();

   
}
