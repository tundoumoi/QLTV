package chatbot;

import DAO.BookDAO;
import Model.Book;
import RecommentSystem.ContentBasedRecommender;
import Swing.CustomerPage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatbotController {
    private BookDAO bookDAO;
    private CustomerPage customerPage; // Tham chiếu tới CustomerPage
    private boolean waitingForPurchaseQuantity = false;
    private String pendingPurchaseBookId = null;
    
    // Trạng thái cho gợi ý sách
    private boolean waitingForRecommendation = false;
    private String pendingRecommendationTarget = null; // có thể là mã hoặc tiêu đề

    public ChatbotController(CustomerPage customerPage) {
        this.customerPage = customerPage;
        this.bookDAO = new BookDAO(); // Khởi tạo đối tượng BookDAO
    }

    public String getResponse(String input) {
        input = input.trim();
        String lower = input.toLowerCase();

        // Nếu đang chờ nhập số lượng cho giao dịch mua
        if (waitingForPurchaseQuantity) {
            try {
                int quantity = Integer.parseInt(input);
                Book book = bookDAO.getById(pendingPurchaseBookId);
                if (book == null) {
                    waitingForPurchaseQuantity = false;
                    pendingPurchaseBookId = null;
                    return "Sách không tồn tại.";
                }
                if (quantity <= 0 || quantity > book.getQuantity()) {
                    return "Số lượng không hợp lệ. Số lượng phải lớn hơn 0 và nhỏ hơn hoặc bằng " 
                            + book.getQuantity() + ". Xin nhập lại số lượng:";
                }
                waitingForPurchaseQuantity = false;
                String response = customerPage.processPurchase(pendingPurchaseBookId, quantity);
                pendingPurchaseBookId = null;
                return response;
            } catch (NumberFormatException ex) {
                return "Xin vui lòng nhập một số nguyên hợp lệ cho số lượng.";
            }
        }

        // Xử lý lệnh gợi ý sách dựa trên các cụm từ "gợi ý", "tương tự", "giống", "giống như"
        if (lower.contains("gợi ý") && (lower.contains("tương tự") || lower.contains("giống như") || lower.contains("giống"))) {
            // Sử dụng regex để trích xuất tiêu đề sách sau một trong các cụm từ này
            Pattern pattern = Pattern.compile("(?:tương tự(?: như)?|giống(?: như)?)\\s+(.*)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                String targetTitle = matcher.group(1).trim();
                if (targetTitle.isEmpty()) {
                    return "Vui lòng nhập tiêu đề của sách làm cơ sở để gợi ý.";
                }
                // Tìm sách theo tiêu đề
                ArrayList<Book> booksFound = bookDAO.findBookByTitle(targetTitle);
                if (booksFound.isEmpty()) {
                    return "Không tìm thấy sách với tiêu đề \"" + targetTitle + "\".";
                }
                Book targetBook = booksFound.get(0);
                List<Book> allBooks = bookDAO.getBookList();
                RecommentSystem.ContentBasedRecommender recommender = new RecommentSystem.ContentBasedRecommender(allBooks);
                List<Book> recommendations = recommender.getRecommendations(targetBook, 3);
                if (recommendations.isEmpty()) {
                    return "Không có gợi ý nào cho sách \"" + targetBook.getTitle() + "\".";
                }
                StringBuilder sb = new StringBuilder("Gợi ý cho sách \"" + targetBook.getTitle() + "\":\n");
                for (Book rec : recommendations) {
                    sb.append(rec.getBookId()).append(" - ").append(rec.getTitle()).append("\n");
                }
                return sb.toString();
            } else {
                return "Xin hãy nhập câu lệnh theo dạng: \"Hãy gợi ý cho tôi một vài cuốn sách tương tự/giống như [tiêu đề sách]\".";
            }
        }

        // Các lệnh khác
        if (lower.contains("danh sách")) {
            return handleBookList();
        } else if (lower.contains("tìm")) {
            return handleSearch(input);
        } else if (lower.contains("mua") && lower.contains("mã")) {
            String bookId = extractBookId(input);
            if (bookId.isEmpty()) {
                return "Không tìm thấy mã sách trong yêu cầu của bạn.";
            }
            Book book = bookDAO.getById(bookId);
            if (book == null) {
                return "Không tìm thấy sách với mã " + bookId + ".";
            }
            pendingPurchaseBookId = bookId;
            waitingForPurchaseQuantity = true;
            return "Bạn muốn mua bao nhiêu quyển? Số lượng tối đa là " + book.getQuantity() + ".";
        } else if (lower.contains("thuê") && lower.contains("mã")) {
            return handleRental(input);
        } else {
            return "Sorry, tôi không hiểu yêu cầu của bạn. Vui lòng nhập 'danh sách', 'tìm kiếm', 'mua/thuê mã [book ID]' hoặc 'gợi ý ...'.";
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

    private String handleRental(String input) {
        String bookId = extractBookId(input);
        if (bookId.isEmpty()) {
            return "Không tìm thấy mã sách trong yêu cầu của bạn.";
        }
        return customerPage.processRental(bookId);
    }

    private String handleBookList() {
        ArrayList<Book> books = bookDAO.getBookList();
        if (books.isEmpty()) {
            return "Thư viện hiện tại không có sách.";
        } else {
            StringBuilder sb = new StringBuilder("Danh sách sách:\n");
            for (Book book : books) {
                sb.append(book.getBookId()).append(" - ").append(book.getTitle()).append("\n");
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
            return "Vui lòng chỉ định tiêu chí tìm kiếm hợp lệ (ví dụ: ngày, tiêu đề, thể loại, ngôn ngữ, tác giả).";
        }
    }
    
    // Các phương thức handleSearchByPublishedDate, handleSearchByTitle, ... giữ nguyên như cũ
    private String handleSearchByPublishedDate(String input) {
        Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = datePattern.matcher(input);
        if (matcher.find()) {
            String dateStr = matcher.group();
            try {
                LocalDate date = LocalDate.parse(dateStr);
                ArrayList<Book> books = bookDAO.findBookByPublishedDate(date);
                return formatBookList(books);
            } catch (Exception e) {
                return "Định dạng ngày không hợp lệ. Vui lòng sử dụng yyyy-MM-dd.";
            }
        }
        return "Không tìm thấy ngày trong yêu cầu của bạn.";
    }
    
    private String handleSearchByTitle(String input) {
        Pattern titlePattern = Pattern.compile("tiêu đề\\s*(?:là\\s*)?(?:[:]\\s*)?(.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = titlePattern.matcher(input);
        if (matcher.find()) {
            String title = matcher.group(1).replace(":", "").trim();
            if (title.isEmpty()) {
                return "Vui lòng cung cấp tiêu đề để tìm kiếm.";
            }
            ArrayList<Book> books = bookDAO.findBookByTitle(title);
            return formatBookList(books);
        }
        return "Không tìm thấy tiêu đề trong yêu cầu của bạn.";
    }
    
    private String handleSearchByType(String input) {
        Pattern typePattern = Pattern.compile("thể loại\\s*(?:là\\s*)?(?:[:]\\s*)?(.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = typePattern.matcher(input);
        if (matcher.find()) {
            String type = matcher.group(1).replace(":", "").trim();
            if (type.isEmpty()) {
                return "Vui lòng cung cấp thể loại để tìm kiếm.";
            }
            ArrayList<Book> books = bookDAO.findBookByType(type);
            return formatBookList(books);
        }
        return "Không tìm thấy thể loại trong yêu cầu của bạn.";
    }
    
    private String handleSearchByLanguage(String input) {
        Pattern langPattern = Pattern.compile("ngôn ngữ\\s*(?:là\\s*)?(?:[:]\\s*)?(.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = langPattern.matcher(input);
        if (matcher.find()) {
            String language = matcher.group(1).replace(":", "").trim();
            if (language.isEmpty()) {
                return "Vui lòng cung cấp ngôn ngữ để tìm kiếm.";
            }
            ArrayList<Book> books = bookDAO.findBookByLanguage(language);
            return formatBookList(books);
        }
        return "Không tìm thấy ngôn ngữ trong yêu cầu của bạn.";
    }
    
    private String handleSearchByAuthor(String input) {
        Pattern authorPattern = Pattern.compile("tác giả\\s*(?:là\\s*)?(?:[:]\\s*)?(.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = authorPattern.matcher(input);
        if (matcher.find()) {
            String author = matcher.group(1).replace(":", "").trim();
            if (author.isEmpty()) {
                return "Vui lòng cung cấp tên tác giả để tìm kiếm.";
            }
            ArrayList<Book> books = bookDAO.findBookByAuthor(author);
            return formatBookList(books);
        }
        return "Không tìm thấy tên tác giả trong yêu cầu của bạn.";
    }
    
    private String formatBookList(ArrayList<Book> books) {
        if (books == null || books.isEmpty()) {
            return "Không tìm thấy sách phù hợp với tiêu chí của bạn.";
        }
        StringBuilder sb = new StringBuilder("Kết quả tìm kiếm:\n");
        for (Book book : books) {
            sb.append(book.getBookId()).append(" - ").append(book.getTitle()).append("\n");
        }
        return sb.toString();
    }
}
