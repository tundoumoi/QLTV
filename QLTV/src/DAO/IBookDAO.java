package DAO;

import Model.Book;
import java.util.ArrayList;


public interface IBookDAO extends GenericDAO<Book, ArrayList<Book>>{
    @Override
    public void delete(String id);

    @Override
    public void update(Book entity);

    @Override
    public void insert(Book entity);

    @Override
    public Book getById(String id);

    @Override
    public ArrayList<Book> getAll();
}
