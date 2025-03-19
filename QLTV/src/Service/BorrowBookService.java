/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.BorrowBookDAO;
import Model.BookBorrow;
import View.view;
import java.util.ArrayList;

/**
 *
 * @author dangt
 */
public class BorrowBookService implements Service<BookBorrow> {
    private final BorrowBookDAO borrowBookDAO = new BorrowBookDAO();
    private ArrayList<BookBorrow> borrowBookList = new ArrayList<>();
    View.view view = new view();
    
    public BorrowBookService() {
        borrowBookList = borrowBookDAO.getAllBorrow();
    }

    @Override
    public BookBorrow findById(String id) {
        for (BookBorrow borrowBook : borrowBookList) {
            if (borrowBook.getCardId().equals(id)) {
                return borrowBook;
            }
        }
        view.message("Invalid id Book");
        return null;
    }

    @Override
    public void insert(BookBorrow borrowBook) {
        borrowBookDAO.insertBorrowB(borrowBook);
        borrowBookList.add(borrowBook);
        
    }

    @Override
    public void delete(String id) {
        BookBorrow borrowBook = findById(id);
        if (borrowBook != null) {
            borrowBookDAO.delete(id);
            borrowBookList.remove(borrowBook);
        }
    }

    @Override
    public void display(BookBorrow borrowBook) {
        System.out.println(borrowBook);
    }

    public void update(BookBorrow borrowBook) {
        borrowBookDAO.update(borrowBook);
        int index = borrowBookList.indexOf(borrowBook);
        if (index != -1) {
            borrowBookList.set(index, borrowBook);
        }
    }

    public ArrayList<BookBorrow> getBorrowBookList() {
        return borrowBookList;
    }
}
