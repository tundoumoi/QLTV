/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Swing;

import DAO.BookDAO;
import DAO.BorrowBookDAO;
import DAO.BuyBookDAO;
import DAO.ReportDAO;
import Model.Book;
import Model.BookBorrow;
import Model.BuyBook;
import Model.Report;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO Ideapad 3
 */
public class CustomerPage extends javax.swing.JFrame {

    private String selectedBookId = null;
    private String selectedBookTitle = null;
    
    /**
     * Creates new form NewJFrame
     */
    public CustomerPage() {
        initComponents();
        populateAvailableBooks();
        Find.addActionListener(this::FindActionPerformed);
        ResultOfFind.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ResultOfFindMouseClicked(evt);
            }
        });
        BUY.addActionListener(this::BUYActionPerformed);
        BORROW.addActionListener(this::BORROWActionPerformed);
        REPPORT.addActionListener(this::REPPORTActionPerformed);

        // Tạo instance của Chatbot (lớp Chatbot kế thừa từ JPanel)
        chatbot.Chatbot chatbotPanel = new chatbot.Chatbot();

        // Đặt layout cho ChatBotPanel nếu cần (ở đây dùng BorderLayout)
        ChatbotPanel.setLayout(new java.awt.BorderLayout());

        // Thêm chatbotPanel vào ChatBotPanel tại vị trí CENTER
        ChatbotPanel.add(chatbotPanel, java.awt.BorderLayout.CENTER);

        // Nếu cần, gọi revalidate() và repaint() để cập nhật giao diện
        ChatbotPanel.revalidate();
        ChatbotPanel.repaint();
    }
    
    private void populateAvailableBooks() {
        // Tạo instance của BookDAO và truy xuất danh sách sách có quantity > 0
        BookDAO bookDAO = new BookDAO();
        ArrayList<Book> availableBooks = bookDAO.getBooksWithQuantityGreaterThanZero();

        // Lấy model của JTable
        DefaultTableModel model = (DefaultTableModel) AvailableBook.getModel();
        model.setRowCount(0); // Xóa các dòng cũ (nếu có)

        // Duyệt qua danh sách và thêm mỗi sách (chỉ lấy title và quantity) vào JTable
        for (Book book : availableBooks) {
            model.addRow(new Object[]{book.getTitle(), book.getQuantity()});
        }
    }

    private void updateResultTable(ArrayList<Book> books) {
        DefaultTableModel model = (DefaultTableModel) ResultOfFind.getModel();
        model.setRowCount(0); 

        for (Book book : books) {
            model.addRow(new Object[]{book.getTitle(), book.getQuantity()});
        }
    }

    private void ResultOfFindMouseClicked(java.awt.event.MouseEvent evt) { 
        int selectedRow = ResultOfFind.getSelectedRow();
        if (selectedRow != -1) {
            selectedBookTitle = ResultOfFind.getValueAt(selectedRow, 0).toString(); // Cột 0 là title
            selectedBookId = findBookIdByTitle(selectedBookTitle); // Tìm bookId từ title
        }
    }
    
    private String findBookIdByTitle(String title) {
        BookDAO bookDAO = new BookDAO();
        ArrayList<Book> books = bookDAO.findBookByTitle(title);
        return books.isEmpty() ? null : books.get(0).getBookId();
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        ChatbotPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        AvailableBook = new javax.swing.JTable();
        InputToFind = new javax.swing.JTextField();
        Find = new javax.swing.JButton();
        SelectedToFind = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        ResultOfFind = new javax.swing.JTable();
        BUY = new javax.swing.JButton();
        BORROW = new javax.swing.JButton();
        REPPORT = new javax.swing.JButton();
        ReportContent = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout ChatbotPanelLayout = new javax.swing.GroupLayout(ChatbotPanel);
        ChatbotPanel.setLayout(ChatbotPanelLayout);
        ChatbotPanelLayout.setHorizontalGroup(
            ChatbotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 368, Short.MAX_VALUE)
        );
        ChatbotPanelLayout.setVerticalGroup(
            ChatbotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CUSTOMER");

        jTabbedPane1.setBackground(new java.awt.Color(255, 204, 255));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        AvailableBook.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title", "Quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(AvailableBook);

        InputToFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputToFindActionPerformed(evt);
            }
        });

        Find.setText("Find");
        Find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FindActionPerformed(evt);
            }
        });

        SelectedToFind.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "bookId", "title", "type", "publishedDate" }));
        SelectedToFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectedToFindActionPerformed(evt);
            }
        });

        ResultOfFind.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title", "Quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(ResultOfFind);

        BUY.setText("BUY");
        BUY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BUYActionPerformed(evt);
            }
        });

        BORROW.setText("BORROW");
        BORROW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BORROWActionPerformed(evt);
            }
        });

        REPPORT.setText("REPORT");
        REPPORT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                REPPORTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(BUY)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BORROW)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(REPPORT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(SelectedToFind, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Find))
                    .addComponent(InputToFind)
                    .addComponent(ReportContent))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(InputToFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Find)
                    .addComponent(SelectedToFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BUY)
                    .addComponent(BORROW)
                    .addComponent(REPPORT))
                .addGap(18, 18, 18)
                .addComponent(ReportContent, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("SERVICE", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 759, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 602, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("MAKING CARD", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ChatbotPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(545, 545, 545))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ChatbotPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BUYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BUYActionPerformed
        // TODO add your handling code here:
        if (selectedBookId == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn một cuốn sách!");
            return;
        }

        String customerId = "C123"; // Lấy ID khách hàng, giả sử đã đăng nhập
        int quantity = 1; // Giả định mua 1 quyển sách
        LocalDate purchaseDate = LocalDate.now();

        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.getById(selectedBookId);
        if (book.getQuantity() < quantity) {
            javax.swing.JOptionPane.showMessageDialog(this, "Sách không đủ số lượng!");
            return;
        }

        // Tạo hóa đơn mua sách
        BuyBook buyBook = new BuyBook("Order" + System.currentTimeMillis(), customerId, selectedBookId, quantity, book.getPrice() * quantity, purchaseDate);
        BuyBookDAO buyBookDAO = new BuyBookDAO();
        buyBookDAO.insertBuyB(buyBook);

        // Cập nhật số lượng sách còn lại
        bookDAO.updateQuantity(selectedBookId, book.getQuantity() - quantity);

        // Ghi lại giao dịch trong ReportContent
        ReportContent.setText("Bạn đã mua sách: " + selectedBookTitle + "\nSố lượng: " + quantity);
    }//GEN-LAST:event_BUYActionPerformed

    private void InputToFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputToFindActionPerformed
        
    }//GEN-LAST:event_InputToFindActionPerformed

    private void SelectedToFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectedToFindActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SelectedToFindActionPerformed

    private void FindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FindActionPerformed
        // TODO add your handling code here:
        String keyword = InputToFind.getText().trim();
        String selectedCriteria = SelectedToFind.getSelectedItem().toString();
        BookDAO bookDAO = new BookDAO();
        ArrayList<Book> result = new ArrayList<>();

        if (keyword.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!");
            return;
        }

        switch (selectedCriteria) {
            case "bookId":
                Book book = bookDAO.getById(keyword);
                if (book != null) {
                    result.add(book);
                }
                break;
            case "title":
                result = bookDAO.findBookByTitle(keyword);
                break;
            case "type":
                result = bookDAO.findBookByType(keyword);
                break;
            case "publishedDate":
                result = bookDAO.findBookByPublishedDate(java.time.LocalDate.parse(keyword));
                break;
            default:
                javax.swing.JOptionPane.showMessageDialog(this, "Tiêu chí tìm kiếm không hợp lệ!");
                return;
        }

        updateResultTable(result);
    }//GEN-LAST:event_FindActionPerformed

    private void BORROWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BORROWActionPerformed
        // TODO add your handling code here:
        if (selectedBookId == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn một cuốn sách!");
            return;
        }

        String cardId = "Card123"; // ID thẻ thư viện của người dùng
        LocalDate borrowDate = LocalDate.now();
        LocalDate endDate = borrowDate.plusDays(14); // Mượn trong 14 ngày

        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.getById(selectedBookId);
        if (book.getQuantity() < 1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Sách không có sẵn để mượn!");
            return;
        }

        // Tạo thông tin mượn sách
        BookBorrow borrow = new BookBorrow(cardId, selectedBookId, borrowDate, endDate);
        BorrowBookDAO borrowBookDAO = new BorrowBookDAO();
        borrowBookDAO.insertBorrowB(borrow);

        // Cập nhật số lượng sách còn lại
        bookDAO.updateQuantity(selectedBookId, book.getQuantity() - 1);

        // Ghi lại giao dịch trong ReportContent
        ReportContent.setText("Bạn đã mượn sách: " + selectedBookTitle + "\nNgày mượn: " + borrowDate + "\nNgày trả: " + endDate);
    }//GEN-LAST:event_BORROWActionPerformed

    private void REPPORTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_REPPORTActionPerformed
        // TODO add your handling code here:
        // Kiểm tra xem đã chọn sách chưa
        if (selectedBookId == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn một cuốn sách để báo cáo!");
            return;
        }

        // Lấy nội dung báo cáo từ ReportContent
        String reportContent = ReportContent.getText().trim();
        if (reportContent.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung báo cáo!");
            return;
        }

        // Lấy customerId hợp lệ (ở đây dùng giá trị mẫu, bạn cần thay bằng thông tin đăng nhập thực tế)
        String customerId = "C123"; 
        LocalDate reportDate = LocalDate.now();

        // Tạo đối tượng Report sử dụng constructor gồm customerId, bookId, title, reportDate và content
        Report report = new Report(customerId, selectedBookId, selectedBookTitle, reportDate, reportContent);

        // Lưu báo cáo vào cơ sở dữ liệu thông qua ReportDAO
        ReportDAO reportDAO = new ReportDAO();
        reportDAO.insert(report);

        // Reset nội dung ReportContent và thông báo thành công
        ReportContent.setText("");
        javax.swing.JOptionPane.showMessageDialog(this, "Báo cáo thành công cho sách: " + selectedBookTitle);
    }//GEN-LAST:event_REPPORTActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CustomerPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable AvailableBook;
    private javax.swing.JButton BORROW;
    private javax.swing.JButton BUY;
    private javax.swing.JPanel ChatbotPanel;
    private javax.swing.JButton Find;
    private javax.swing.JTextField InputToFind;
    private javax.swing.JButton REPPORT;
    private javax.swing.JTextField ReportContent;
    private javax.swing.JTable ResultOfFind;
    private javax.swing.JComboBox<String> SelectedToFind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
