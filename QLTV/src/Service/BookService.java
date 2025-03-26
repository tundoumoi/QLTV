package Service;

import DAO.BookDAO;
import Model.Book;
import View.view;
import java.util.HashMap;
import java.util.List;

public class BookService implements Service<Book> {

    private final BookDAO bookDAO = new BookDAO();
    private HashMap<String, Book> bookMap = new HashMap<>();
    View.view view = new view();

    public BookService() {
        List<Book> bookList = bookDAO.getAll();
        for (Book book : bookList) {
            bookMap.put(book.getBookId(), book);
        }
    }

    public String increaseBOOKID() {
        int count = bookDAO.getBookCount()+1;
        return String.format("B%04d", count);
    }

    @Override
    public Book findById(String id) {
        Book book = bookDAO.getById(id);
        if (book == null) {
            view.message("Invalid id Book");
        }
        return book;
    }

    public boolean insert1(Book book) {
        return bookDAO.insert1(book);

    }

    @Override
    public void delete(String id) {
        if (bookMap.containsKey(id)) {
            bookDAO.delete(id);
            bookMap.remove(id);
        } else {
            view.message("Invalid id Book");
        }
    }

    @Override
    public void display(Book book) {
        System.out.println(book);
    }

    public void update(Book book) {
            bookDAO.update(book);
            bookMap.put(book.getBookId(), book);

    }

    public HashMap<String, Book> getBookMap() {
        bookMap.clear();
        List<Book> bookList = bookDAO.getAll();
        for (Book book : bookList) {
            bookMap.put(book.getBookId(), book);
        }
        return bookMap;
    }

    @Override
    public void insert(Book entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
