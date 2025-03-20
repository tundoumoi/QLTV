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
        int count = bookMap.size() + 1;
        return String.format("B%03d", count);
    }


    @Override
    public Book findById(String id) {
        Book book = bookMap.get(id);
        if (book == null) {
            view.message("Invalid id Book");
        }
        return book;
    }

    @Override
    public void insert(Book book) {
        if (!bookMap.containsKey(book.getBookId())) {
            bookDAO.insert(book);
            bookMap.put(book.getBookId(), book);
        } else {
            view.message("Book id da ton tai");
        }
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
        if (bookMap.containsKey(book.getBookId())) {
            bookDAO.update(book);
            bookMap.put(book.getBookId(), book);
        } else {
            view.message("Invalid id Book");
        }
    }

    public HashMap<String, Book> getBookMap() {
        bookMap.clear();
        List<Book> bookList = bookDAO.getAll();
        for (Book book : bookList) {
            bookMap.put(book.getBookId(), book);
        }
        return bookMap;
    }
}
