/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RecommentSystem;

import Model.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author LENOVO Ideapad 3
 */

public class ContentBasedRecommender {
    
    private List<Book> books;
    private Map<Book, Map<String, Double>> bookVectors; // Lưu vector TF-IDF của từng sách
    private Map<String, Double> globalIDF; // Giá trị IDF toàn cục

    // Hàm khởi tạo nhận danh sách sách (có thể lấy từ BookDAO)
    public ContentBasedRecommender(List<Book> books) {
        this.books = books;
        this.globalIDF = computeGlobalIDF(books);
        this.bookVectors = new HashMap<>();
        for (Book book : books) {
            String content = getBookContent(book);
            Map<String, Double> tf = computeTF(content);
            Map<String, Double> tfidf = computeTFIDF(tf, globalIDF);
            bookVectors.put(book, tfidf);
        }
    }
    
    // Hàm này lấy nội dung văn bản của một cuốn sách bằng cách kết hợp các thuộc tính
    private String getBookContent(Book book) {
        // Kết hợp các trường có tính mô tả: title, author, publisher, type, language
        return (book.getTitle() + " " + book.getAuthor() + " " + book.getPublisher() + " " 
                + book.getType() + " " + book.getLanguage()).toLowerCase();
    }
    
    // Hàm tính TF (term frequency) của một chuỗi văn bản
    private Map<String, Double> computeTF(String text) {
        Map<String, Double> tf = new HashMap<>();
        // Tách văn bản theo các ký tự không phải chữ hoặc số
        String[] tokens = text.split("\\W+");
        int totalTokens = 0;
        for (String token : tokens) {
            if (token.isEmpty()) continue;
            totalTokens++;
            tf.put(token, tf.getOrDefault(token, 0.0) + 1.0);
        }
        // Chuẩn hóa tần số theo tổng số token
        for (Map.Entry<String, Double> entry : tf.entrySet()) {
            tf.put(entry.getKey(), entry.getValue() / totalTokens);
        }
        return tf;
    }
    
    // Hàm tính giá trị IDF toàn cục cho danh sách sách
    private Map<String, Double> computeGlobalIDF(List<Book> books) {
        Map<String, Integer> docCount = new HashMap<>();
        int totalDocs = books.size();
        for (Book book : books) {
            Set<String> seen = new HashSet<>();
            String content = getBookContent(book);
            String[] tokens = content.split("\\W+");
            for (String token : tokens) {
                if (token.isEmpty()) continue;
                if (!seen.contains(token)) {
                    docCount.put(token, docCount.getOrDefault(token, 0) + 1);
                    seen.add(token);
                }
            }
        }
        Map<String, Double> idf = new HashMap<>();
        // IDF = log(totalDocs / df)
        for (Map.Entry<String, Integer> entry : docCount.entrySet()) {
            idf.put(entry.getKey(), Math.log((double) totalDocs / entry.getValue()));
        }
        return idf;
    }
    
    // Hàm chuyển đổi TF vector thành TF-IDF vector sử dụng global IDF
    private Map<String, Double> computeTFIDF(Map<String, Double> tf, Map<String, Double> idf) {
        Map<String, Double> tfidf = new HashMap<>();
        for (Map.Entry<String, Double> entry : tf.entrySet()) {
            String term = entry.getKey();
            double tfVal = entry.getValue();
            double idfVal = idf.getOrDefault(term, 0.0);
            tfidf.put(term, tfVal * idfVal);
        }
        return tfidf;
    }
    
    // Hàm tính cosine similarity giữa hai vector
    private double cosineSimilarity(Map<String, Double> vec1, Map<String, Double> vec2) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;
        
        for (String key : vec1.keySet()) {
            double v1 = vec1.get(key);
            norm1 += v1 * v1;
            if (vec2.containsKey(key)) {
                dotProduct += v1 * vec2.get(key);
            }
        }
        for (double v : vec2.values()) {
            norm2 += v * v;
        }
        if (norm1 == 0.0 || norm2 == 0.0) return 0.0;
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
    
    // Hàm lấy gợi ý sách dựa trên một cuốn sách mục tiêu
    public List<Book> getRecommendations(Book targetBook, int numRecommendations) {
        Map<String, Double> targetVector = bookVectors.get(targetBook);
        // Nếu sách mục tiêu chưa được tính vector (không có trong danh sách), tính toán ngay
        if (targetVector == null) {
            String content = getBookContent(targetBook);
            targetVector = computeTFIDF(computeTF(content), globalIDF);
        }
        // Tính độ tương đồng của sách mục tiêu với các sách khác
        Map<Book, Double> similarities = new HashMap<>();
        for (Book book : books) {
            if (book.equals(targetBook)) continue; // Bỏ qua sách mục tiêu
            Map<String, Double> vector = bookVectors.get(book);
            double similarity = cosineSimilarity(targetVector, vector);
            similarities.put(book, similarity);
        }
        // Sắp xếp các sách theo độ tương đồng giảm dần
        List<Book> sortedBooks = similarities.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        // Trả về số lượng gợi ý theo yêu cầu
        return sortedBooks.subList(0, Math.min(numRecommendations, sortedBooks.size()));
    }
    
    // Phương thức main để kiểm tra chức năng của hệ thống gợi ý
    public static void main(String[] args) {
        // Tạo một số cuốn sách mẫu (trong thực tế, bạn có thể lấy từ BookDAO)
        List<Book> books = new ArrayList<>();
//        books.add(new Book("1", "Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Bloomsbury", java.time.LocalDate.of(1997, 6, 26), 20.0, 100, "Fantasy", "English"));
//        books.add(new Book("2", "Harry Potter and the Chamber of Secrets", "J.K. Rowling", "Bloomsbury", java.time.LocalDate.of(1998, 7, 2), 22.0, 100, "Fantasy", "English"));
//        books.add(new Book("3", "The Hobbit", "J.R.R. Tolkien", "Allen & Unwin", java.time.LocalDate.of(1937, 9, 21), 15.0, 50, "Fantasy", "English"));
//        books.add(new Book("4", "A Brief History of Time", "Stephen Hawking", "Bantam", java.time.LocalDate.of(1988, 4, 1), 18.0, 70, "Science", "English"));
//        
        ContentBasedRecommender recommender = new ContentBasedRecommender(books);
        
        // Lấy gợi ý cho sách mục tiêu đầu tiên
        Book target = books.get(0);
        List<Book> recommendations = recommender.getRecommendations(target, 3);
        
        System.out.println("Recommendations for: " + target.getTitle());
        for (Book rec : recommendations) {
            System.out.println(rec.getTitle());
        }
    }
}

