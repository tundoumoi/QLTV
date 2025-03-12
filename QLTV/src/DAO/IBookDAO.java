/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
<<<<<<< HEAD
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

<<<<<<< HEAD
import Model.Book;
import java.util.HashMap;
=======
import java.awt.print.Book;
import java.util.TreeSet;
>>>>>>> 1b12b1a728f7ee688ca170402123c382533880dc

/**
 *
 * @author NHAT NAM
 */
<<<<<<< HEAD
public interface IBookDAO extends GenericDAO<Book, HashMap>{
    
=======
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

   
>>>>>>> 1b12b1a728f7ee688ca170402123c382533880dc
}
