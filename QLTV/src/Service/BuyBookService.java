package Service;

import DAO.BookDAO;
import DAO.BuyBookDAO;
import Model.BuyBook;
import java.util.ArrayList;

/**
 *
 * @author dangt
 */
public class BuyBookService implements Service<BuyBook> {
    private final BuyBookDAO buyBookDAO = new BuyBookDAO();
    private ArrayList<BuyBook> buyBookList = new ArrayList<>();

    public BuyBookService() {
        buyBookList = buyBookDAO.getAllBuy();
    }

    @Override
    public BuyBook findById(String id) {
        for (BuyBook buyBook : buyBookList) {
            if (buyBook.getBookId().equals(id)) {
                return buyBook;
            }
        }
        System.out.println("Invalid id.");
        return null;
    }

    @Override
    public BuyBook insert(BuyBook buyBook) {
        buyBookDAO.insertBuyB(buyBook);
        buyBookList.add(buyBook);
        return buyBook;
    }

    @Override
    public void delete(String id) {
        BuyBook buyBook = findById(id);
        if (buyBook != null) {
            buyBookDAO.delete(id);
            buyBookList.remove(buyBook);
        }
    }

    @Override
    public void display(BuyBook buyBook) {
        System.out.println(buyBook);
    }

    public void update(BuyBook buyBook) {
        buyBookDAO.update(buyBook);
        int index = buyBookList.indexOf(buyBook);
        if (index != -1) {
            buyBookList.set(index, buyBook);
        }
    }

    public ArrayList<BuyBook> getBuyBookList() {
        return buyBookList;
    }
}
