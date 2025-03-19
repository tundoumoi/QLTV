package Service;

import DAO.BookDAO;
import Model.Book;
import View.view;
import java.util.ArrayList;

public class BookService implements Service<Book> {
    private final BookDAO bookDAO = new BookDAO();
    private ArrayList<Book> bookList = new ArrayList<>();
    View.view view = new view();

    public BookService() {
        bookList = bookDAO.getAll();
    }

    @Override
    public Book findById(String id) {
        for (Book book : bookList) {
            if (book.getBookId().equals(id)) {
                return book;
            }
        }
        view.message("Invalid id Book");
        return null;
    }

    @Override
    public Book insert(Book book) {
        bookDAO.insert(book);
        bookList.add(book);
        return book;
    }

    @Override
    public void delete(String id) {
        Book book = findById(id);
        if (book != null) {
            bookDAO.delete(id);
            bookList.remove(book);
        }
    }

    @Override
    public void display(Book book) {
        System.out.println(book);
    }

    public void update(Book book) {
        bookDAO.update(book);
        int index = bookList.indexOf(book);
        if (index != -1) {
            bookList.set(index, book);
        }
    }

    public ArrayList<Book> getBookList() {
        bookList.clear();
        bookList = bookDAO.getAll();
        return bookList;
    }
}
