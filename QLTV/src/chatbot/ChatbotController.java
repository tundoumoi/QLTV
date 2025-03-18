package chatbot;

import DAO.BookDAO;
import Model.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatbotController {
    private BookDAO bookDAO;

    public ChatbotController() {
        bookDAO = new BookDAO();
    }

    public String getResponse(String input) {
        input = input.toLowerCase().trim();
        
        if (input.contains("danh sách")) {
            return handleBookList();
        } else if (input.contains("tìm")) {
            return handleSearch(input);
        } else if (input.contains("mua") && input.contains("mã")) {
            return handlePurchase(input);
        } else if (input.contains("thuê") && input.contains("mã")) {
            return handleRental(input);
        } else {
            return "Sorry, I don't understand your request. Please enter 'danh sách' to view books, 'tìm kiếm' followed by search criteria (ví dụ: tìm kiếm theo tiêu đề: Harry Potter, ngày: 2023-03-20, tác giả: J.K. Rowling, ...), or 'mua/thuê mã [book ID]' to make a transaction.";
        }
    }

    private String handleBookList() {
        ArrayList<Book> books = bookDAO.getBookList();
        if (books.isEmpty()) {
            return "The library currently has no books.";
        } else {
            StringBuilder sb = new StringBuilder("List of books:\n");
            for (Book book : books) {
                sb.append(book.getBookId())
                  .append(" - ")
                  .append(book.getTitle())
                  .append("\n");
            }
            return sb.toString();
        }
    }
    
    private String handleSearch(String input) {
        if (input.contains("ngày")) {
            return handleSearchByPublishedDate(input);
        } else if (input.contains("tên")) {
            return handleSearchByTitle(input);
        } else if (input.contains("thể loại")) {
            return handleSearchByType(input);
        } else if (input.contains("ngôn ngữ") || input.contains("tiếng")) {
            return handleSearchByLanguage(input);
        } else if (input.contains("tác giả")) {
            return handleSearchByAuthor(input);
        } else {
            return "Please specify a valid search criteria such as 'ngày', 'tiêu đề', 'thể loại', 'ngôn ngữ', or 'tác giả'.";
        }
    }
    
    private String handleSearchByPublishedDate(String input) {
        // Tìm kiếm theo định dạng yyyy-MM-dd
        Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = datePattern.matcher(input);
        if (matcher.find()) {
            String dateStr = matcher.group();
            try {
                LocalDate date = LocalDate.parse(dateStr);
                ArrayList<Book> books = bookDAO.findBookByPublishedDate(date);
                return formatBookList(books);
            } catch (Exception e) {
                return "Invalid date format. Please use yyyy-MM-dd.";
            }
        }
        return "No date found in your search query.";
    }
    
    private String handleSearchByTitle(String input) {
        // Regex mới: bỏ qua từ "là" và dấu ":" nếu có
        Pattern titlePattern = Pattern.compile("tiêu đề\\s*(?:là\\s*)?(?:[:]\\s*)?(.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = titlePattern.matcher(input);
        if (matcher.find()) {
            String title = matcher.group(1).replace(":", "").trim();
            if (title.isEmpty()) {
                return "Please provide a title to search.";
            }
            ArrayList<Book> books = bookDAO.findBookByTitle(title);
            return formatBookList(books);
        }
        return "No title provided in your search query.";
    }
    
    private String handleSearchByType(String input) {
        // Regex mới: bỏ qua từ "là" và dấu ":" nếu có
        Pattern typePattern = Pattern.compile("thể loại\\s*(?:là\\s*)?(?:[:]\\s*)?(.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = typePattern.matcher(input);
        if (matcher.find()) {
            String type = matcher.group(1).replace(":", "").trim();
            if (type.isEmpty()) {
                return "Please provide a type to search.";
            }
            ArrayList<Book> books = bookDAO.findBookByType(type);
            return formatBookList(books);
        }
        return "No type provided in your search query.";
    }
    
    private String handleSearchByLanguage(String input) {
        Pattern langPattern = Pattern.compile("ngôn ngữ\\s*(?:là\\s*)?(?:[:]\\s*)?(.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = langPattern.matcher(input);
        if (matcher.find()) {
            String language = matcher.group(1).replace(":", "").trim();
            if (language.isEmpty()) {
                return "Please provide a language to search.";
            }
            ArrayList<Book> books = bookDAO.findBookByLanguage(language);
            return formatBookList(books);
        }
        return "No language provided in your search query.";
    }
    
    private String handleSearchByAuthor(String input) {
        // Regex mới: bỏ qua từ "là" và dấu ":" nếu có
        Pattern authorPattern = Pattern.compile("tác giả\\s*(?:là\\s*)?(?:[:]\\s*)?(.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = authorPattern.matcher(input);
        if (matcher.find()) {
            String author = matcher.group(1).replace(":", "").trim();
            if (author.isEmpty()) {
                return "Please provide an author to search.";
            }
            ArrayList<Book> books = bookDAO.findBookByAuthor(author);
            return formatBookList(books);
        }
        return "No author provided in your search query.";
    }
    
    private String formatBookList(ArrayList<Book> books) {
        if (books == null || books.isEmpty()) {
            return "No books found matching your search criteria.";
        }
        StringBuilder sb = new StringBuilder("Search results:\n");
        for (Book book : books) {
            sb.append(book.getBookId())
              .append(" - ")
              .append(book.getTitle())
              .append("\n");
        }
        return sb.toString();
    }

    private String handlePurchase(String input) {
        String bookId = extractBookId(input);
        if (bookId.isEmpty()) {
            return "The book code in your request was not found.";
        }
        Book book = bookDAO.getById(bookId);
        if (book != null) {
            // Có thể thêm logic đặt hàng ở đây
            return "Book " + book.getTitle() + " has been added to your cart. Please confirm payment.";
        } else {
            return "No book found with ID " + bookId + ".";
        }
    }

    private String handleRental(String input) {
        String bookId = extractBookId(input);
        if (bookId.isEmpty()) {
            return "The book code in your request was not found.";
        }
        Book book = bookDAO.getById(bookId);
        if (book != null) {
            // Có thể thêm logic cập nhật thuê ở đây
            return "Book " + book.getTitle() + " has been booked. Please confirm your rental information.";
        } else {
            return "No book found with ID " + bookId + ".";
        }
    }

    private String extractBookId(String input) {
        Pattern pattern = Pattern.compile("mã\\s*(?:là\\s*)?([A-Za-z0-9]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
             return matcher.group(1);
        }
        return "";
    }
}
