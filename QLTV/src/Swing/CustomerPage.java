package Swing;

import DAO.BillDAO;
import DAO.BookDAO;
import DAO.BorrowBookDAO;
import DAO.BuyBookDAO;
import DAO.CustomerBorrowDAO;
import DAO.CustomerDAO;
import DAO.DatabaseConnection;
import DAO.ReportDAO;
import Model.Book;
import Model.BookBorrow;
import Model.BuyBook;
import Model.Customer;
import Model.CustomerBorrow;
import Model.Report;
import Service.CustomerService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class CustomerPage extends javax.swing.JFrame {

    private DefaultTableModel modelBuy;
    private String customerId;
    private String selectedBookId = null;
    private String selectedBookTitle = null;
    private boolean eventListenersAdded = false; // Kiểm tra xem đã thêm sự kiện hay chưa
    private Customer customer;
    private CustomerService cusSer = new CustomerService();
    /**
     * Creates new form NewJFrame
     */
    public CustomerPage(String customerId) {
        this.customerId = customerId;
       customer = cusSer.findById(customerId);
        initComponents();
        populateAvailableBooks();
        addEventListeners();
        
        // Khởi tạo Chatbot panel (nếu cần)
        chatbot.Chatbot chatbotPanel = new chatbot.Chatbot(this);
        ChatbotPanel.setLayout(new java.awt.BorderLayout());
        ChatbotPanel.add(chatbotPanel, java.awt.BorderLayout.CENTER);
        ChatbotPanel.revalidate();
        ChatbotPanel.repaint();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void addEventListeners() {
        if (!eventListenersAdded) {
            Find.addActionListener(this::FindActionPerformed);
            ResultOfFind.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    ResultOfFindMouseClicked(evt);
                }
            });
            
            eventListenersAdded = true;
        }
    }
    
    private void populateAvailableBooks() {
        BookDAO bookDAO = new BookDAO(); 
        ArrayList<Book> availableBooks = bookDAO.getBooksWithQuantityGreaterThanZero(); 
        DefaultTableModel model = (DefaultTableModel) AvailableBook.getModel(); 
        model.setRowCount(0); for (Book book : availableBooks) { 
            model.addRow(new Object[]{book.getBookId(), book.getTitle(), book.getType(), book.getPrice()}); 
        }    
    }

    private void updateResultTable(ArrayList<Book> books) {
        DefaultTableModel model = (DefaultTableModel) ResultOfFind.getModel();
        model.setRowCount(0);
        for (Book book : books) {
            model.addRow(new Object[]{book.getTitle(), book.getQuantity()});
        }
    }
    
    public void updateTableBuy() {
        BuyBookDAO buyBookDAO = new BuyBookDAO();
        ArrayList<BuyBook> buyBooks = buyBookDAO.getAllBuy();
        modelBuy = new DefaultTableModel(new String[]{"Order ID", "Book ID", "Quantity"}, 0);
        modelBuy.setRowCount(0);
        for (BuyBook b : buyBooks) {
            System.out.println("Đơn hàng: " + b.getOrderId() + " - " + b.getBookId() + " - " + b.getQuantity());
            modelBuy.addRow(new Object[]{b.getOrderId(), b.getBookId(), b.getQuantity()});
        }
        // Nếu có bảng TableBuy trên giao diện, cập nhật model
        TableBuy.setModel(modelBuy);
    }
    
    private void ResultOfFindMouseClicked(java.awt.event.MouseEvent evt) { 
        int selectedRow = ResultOfFind.getSelectedRow();
        if (selectedRow != -1) {
            selectedBookTitle = ResultOfFind.getValueAt(selectedRow, 0).toString(); // Lấy title từ cột đầu tiên
            selectedBookId = findBookIdByTitle(selectedBookTitle);
        }
    }
    
    private String findBookIdByTitle(String title) {
        BookDAO bookDAO = new BookDAO();
        ArrayList<Book> books = bookDAO.findBookByTitle(title);
        return books.isEmpty() ? null : books.get(0).getBookId();
    }
    
    public String getCustomerIdByUsername(String username) {
        Customer customer = CustomerDAO.findByUsername(username);
        return customer != null ? customer.getId() : null;
    }
    
    public String processPurchase(String bookId, int quantity) {
        if (bookId == null) {
            return "Vui lòng chọn một cuốn sách!";
        }
        if (customerId == null || customerId.isEmpty()) {
            return "Error: Unable to determine customerId!";
        }

        CustomerDAO customerDAO = new CustomerDAO();
        if (!customerDAO.isCustomerInCustomerBuy(customerId)) {
            // Nếu customer chưa có trong CustomerBuy, thêm vào trước
            String insertSQL = "INSERT INTO CustomerBuy (Cid, totalPurchase, membershipLevel) VALUES (?, 0, 'Standard')";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                 pstmt.setString(1, customerId);
                 pstmt.executeUpdate();
            } catch (SQLException e) {
                 e.printStackTrace();
                 return "Error adding customer to CustomerBuy!";
            }
        }

        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.getById(bookId);

        if (book == null || book.getQuantity() < quantity) {
            return "Sách không đủ số lượng hoặc không tồn tại!";
        }

        LocalDate purchaseDate = LocalDate.now();
        // Tạo đơn mua, cho orderId rỗng để hệ thống tự sinh nếu cần.
        BuyBook buyBook = new BuyBook("", customerId, bookId, quantity, book.getPrice() * quantity, purchaseDate);
        BuyBookDAO buyBookDAO = new BuyBookDAO();
        buyBookDAO.insertBuyB(buyBook);
        bookDAO.updateQuantity(bookId, book.getQuantity() - quantity);

        // Nếu cần cập nhật lại giao diện bảng đơn mua, gọi updateTableBuy()
        updateTableBuy();

        return "Bạn đã mua sách: " + book.getTitle() + "\nSố lượng: " + quantity;
    }
    
    private double calculateCurrentPurchase() {
        double sum = 0;
        for (int i = 0; i < TableBuy.getRowCount(); i++) {
            // Giả sử cột thứ 3 chứa quantity và bạn có thể truy xuất giá trị giá sách từ database hoặc bảng khác
            int quantity = Integer.parseInt(TableBuy.getValueAt(i, 2).toString());
            // Bạn cần có cách lấy giá sách tương ứng với BookId (cột thứ 2)
            String bookId = TableBuy.getValueAt(i, 1).toString();
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.getById(bookId);
            if (book != null) {
                sum += book.getPrice() * quantity;
            }
        }
        return sum;
    }
    
    public String processRental(String bookId) {
        // Kiểm tra thẻ thư viện của khách hàng
        String cardId = "Card" + customerId.substring(1);
        CustomerBorrowDAO cbDao = new CustomerBorrowDAO();
        CustomerBorrow cusBorrow = cbDao.getById(cardId);

        if (cusBorrow == null) {
            return "Bạn chưa có thẻ thư viện. Phải tạo thẻ thư viện trước.";
        }
        if (bookId == null) {
            return "Vui lòng chọn một cuốn sách!";
        }

        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.getById(bookId);
        if (book == null || book.getQuantity() < 1) {
            return "Sách không có sẵn để mượn!";
        }

        // Tính phí mượn (ví dụ: 1/10 giá bán)
        double rentalPrice = book.getPrice() / 10;
        if (cusBorrow.getCardValue() < rentalPrice) {
            return "Thẻ của bạn không đủ số dư. Hãy nạp lại thẻ.";
        }

        LocalDate borrowDate = LocalDate.now();
        LocalDate endDate = borrowDate.plusDays(7);
        BookBorrow borrow = new BookBorrow(cardId, bookId, borrowDate, endDate);
        BorrowBookDAO borrowBookDAO = new BorrowBookDAO();
        borrowBookDAO.insertBorrowB(borrow);
        bookDAO.updateQuantity(bookId, book.getQuantity() - 1);

        // Sau khi mượn thành công, trừ số dư thẻ theo phí mượn
        double newBalance = cusBorrow.getCardValue() - rentalPrice;
        cusBorrow.setCardValue(newBalance);
        cbDao.update(cusBorrow);
        
        DefaultTableModel borrowModel = (DefaultTableModel) TableBorrow.getModel();
        borrowModel.addRow(new Object[]{book.getTitle(), rentalPrice, borrowDate, endDate});

        return "Bạn đã mượn sách: " + book.getTitle() + "\nNgày mượn: " + borrowDate + "\nNgày trả: " + endDate;
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
        TotalPurchaseAndMakingCard = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableBuy = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableBorrow = new javax.swing.JTable();
        PAY = new javax.swing.JButton();
        CreateCard = new javax.swing.JButton();
        TypeCard = new javax.swing.JComboBox<>();
        RECHARGE = new javax.swing.JButton();
        QuantityRecharge = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        Bill = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        AvailableBook = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        InputToFind = new javax.swing.JTextField();
        SelectedToFind = new javax.swing.JComboBox<>();
        Find = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ResultOfFind = new javax.swing.JTable();
        BORROW = new javax.swing.JButton();
        BUY = new javax.swing.JButton();
        REPPORT = new javax.swing.JButton();
        ReportContent = new javax.swing.JTextField();
        InputQuantity = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        Cusinfo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Logout = new javax.swing.JLabel();
        ChatbotPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        TotalPurchaseAndMakingCard.setBackground(new java.awt.Color(153, 153, 153));
        TotalPurchaseAndMakingCard.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        TotalPurchaseAndMakingCard.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("TOTAL PURCHASE");

        TableBuy.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title", "Price", "Quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(TableBuy);

        TableBorrow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title", "Price", "StartBorrowDate", "EndBorrowDate"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(TableBorrow);

        PAY.setText("PAY");
        PAY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PAYActionPerformed(evt);
            }
        });

        CreateCard.setText("CREATE");
        CreateCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateCardActionPerformed(evt);
            }
        });

        TypeCard.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ĐỒNG", "BẠC", "VÀNG" }));

        RECHARGE.setText("RECHARGE");
        RECHARGE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RECHARGEActionPerformed(evt);
            }
        });

        QuantityRecharge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuantityRechargeActionPerformed(evt);
            }
        });

        Bill.setColumns(20);
        Bill.setRows(5);
        jScrollPane5.setViewportView(Bill);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(PAY)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RECHARGE)
                        .addGap(18, 18, 18)
                        .addComponent(CreateCard)
                        .addGap(18, 18, 18)
                        .addComponent(QuantityRecharge, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(TypeCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PAY)
                    .addComponent(CreateCard)
                    .addComponent(TypeCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RECHARGE)
                    .addComponent(QuantityRecharge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );

        TotalPurchaseAndMakingCard.addTab("TOTAL PURCHASE", jPanel4);

        AvailableBook.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "BookId", "Title", "Type", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(AvailableBook);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel3.setText("BOOK");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Enter Book here:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/down-arrow.png"))); // NOI18N

        InputToFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputToFindActionPerformed(evt);
            }
        });

        SelectedToFind.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        SelectedToFind.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "bookId", "title", "type", "publishedDate" }));
        SelectedToFind.setSelectedItem(null);
        SelectedToFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectedToFindActionPerformed(evt);
            }
        });

        Find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/find.png"))); // NOI18N
        Find.setText("Find");
        Find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FindActionPerformed(evt);
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

        BORROW.setText("BORROW");
        BORROW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BORROWActionPerformed(evt);
            }
        });

        BUY.setText("BUY");
        BUY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BUYActionPerformed(evt);
            }
        });

        REPPORT.setText("REPORT");
        REPPORT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                REPPORTActionPerformed(evt);
            }
        });

        ReportContent.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        InputQuantity.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(jLabel3)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(InputToFind)
                        .addGap(48, 48, 48))))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ReportContent))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(InputQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88)
                .addComponent(BUY)
                .addGap(18, 18, 18)
                .addComponent(BORROW)
                .addGap(18, 18, 18)
                .addComponent(REPPORT)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(SelectedToFind, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Find)
                .addGap(104, 104, 104))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel5))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InputToFind, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SelectedToFind, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Find))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InputQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BUY)
                    .addComponent(BORROW)
                    .addComponent(REPPORT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ReportContent, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(151, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TotalPurchaseAndMakingCard.addTab("SERVICE", jPanel3);

        jPanel2.setBackground(new java.awt.Color(0, 153, 204));

        Cusinfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/profile.png"))); // NOI18N
        Cusinfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                CusinfoMousePressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CUSTOMER");

        Logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/log-out-outline-icon.png"))); // NOI18N
        Logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                LogoutMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(Cusinfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 482, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(429, 429, 429)
                .addComponent(Logout)
                .addGap(49, 49, 49))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(Cusinfo))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(Logout)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ChatbotPanelLayout = new javax.swing.GroupLayout(ChatbotPanel);
        ChatbotPanel.setLayout(ChatbotPanelLayout);
        ChatbotPanelLayout.setHorizontalGroup(
            ChatbotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        ChatbotPanelLayout.setVerticalGroup(
            ChatbotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(TotalPurchaseAndMakingCard, javax.swing.GroupLayout.PREFERRED_SIZE, 1121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChatbotPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TotalPurchaseAndMakingCard, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ChatbotPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BUYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BUYActionPerformed
        if (selectedBookId == null) {
            JOptionPane.showMessageDialog(this, "Please select a book!");
            return;
        }
        
        if (customerId == null || customerId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error: Unable to determine customerId!");
            return;
        }
        
        CustomerDAO customerDAO = new CustomerDAO();
        if (!customerDAO.isCustomerInCustomerBuy(customerId)) {
            // Nếu customer chưa có trong CustomerBuy, thêm vào trước
            String insertSQL = "INSERT INTO CustomerBuy (Cid, totalPurchase, membershipLevel) VALUES (?, 0, 'Standard')";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                 pstmt.setString(1, customerId);
                 pstmt.executeUpdate();
            } catch (SQLException e) {
                 e.printStackTrace();
                 JOptionPane.showMessageDialog(this, "Error adding customer to CustomerBuy!");
                 return;
            }
        }
        
        int quantity;
        try {
            quantity = (int)InputQuantity.getValue();
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên hợp lệ cho số lượng!");
            return;
        }
        
        LocalDate purchaseDate = LocalDate.now();
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.getById(selectedBookId);
        
        if (book == null || book.getQuantity() < quantity) {
            JOptionPane.showMessageDialog(this, "Sách không đủ số lượng hoặc không tồn tại!");
            return;
        }
        
        // Truyền chuỗi rỗng cho orderId để DAO tự sinh UUID nếu cần.
        BuyBook buyBook = new BuyBook("", customerId, selectedBookId, quantity, book.getPrice() * quantity, purchaseDate);
        BuyBookDAO buyBookDAO = new BuyBookDAO();
        buyBookDAO.insertBuyB(buyBook);
        bookDAO.updateQuantity(selectedBookId, book.getQuantity() - quantity);
        
        DefaultTableModel buyModel = (DefaultTableModel) TableBuy.getModel();
        buyModel.addRow(new Object[]{buyBook.getOrderId(), selectedBookId, quantity});
        ReportContent.setText("Bạn đã mua sách: " + selectedBookTitle + "\nSố lượng: " + quantity);
        
        // Cập nhật lại bảng đơn mua
        updateTableBuy();
    }//GEN-LAST:event_BUYActionPerformed

    private void InputToFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputToFindActionPerformed
        
    }//GEN-LAST:event_InputToFindActionPerformed

    private void SelectedToFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectedToFindActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SelectedToFindActionPerformed

    private void FindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FindActionPerformed
        String keyword = InputToFind.getText().trim();
        String selectedCriteria = SelectedToFind.getSelectedItem().toString();
        BookDAO bookDAO = new BookDAO();
        ArrayList<Book> result = new ArrayList<>();
        
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!");
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
                try {
                    LocalDate date = LocalDate.parse(keyword);
                    result = bookDAO.findBookByPublishedDate(date);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ! Vui lòng nhập theo định dạng yyyy-MM-dd.");
                    return;
                }
                break;
            default:
                JOptionPane.showMessageDialog(this, "Tiêu chí tìm kiếm không hợp lệ!");
                return;
        }
        
        updateResultTable(result);
    }//GEN-LAST:event_FindActionPerformed

    private void BORROWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BORROWActionPerformed
        String cardId = "Card" + customerId.substring(1); // hoặc cách xác định khác nếu bạn đã có quy ước rõ ràng
        CustomerBorrowDAO cbDao = new CustomerBorrowDAO();
        CustomerBorrow cusBorrow = cbDao.getById(cardId);

        if (cusBorrow == null) {
            JOptionPane.showMessageDialog(this, "Bạn chưa có thẻ thư viện. Phải tạo thẻ thư viện trước.");
            return;
        }
        if (selectedBookId == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một cuốn sách!");
            return;
        }

        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.getById(selectedBookId);
        if (book == null || book.getQuantity() < 1) {
            JOptionPane.showMessageDialog(this, "Sách không có sẵn để mượn!");
            return;
        }

        // Tính phí mượn (ví dụ: 1/10 giá bán)
        double rentalPrice = book.getPrice() / 10;
        // Kiểm tra số dư trong thẻ có đủ để mượn hay không
        if (cusBorrow.getCardValue() < rentalPrice) {
            JOptionPane.showMessageDialog(this, "Thẻ của bạn không đủ số dư. Hãy nạp lại thẻ.");
            return;
        }

        LocalDate borrowDate = LocalDate.now();
        LocalDate endDate = borrowDate.plusDays(7);
        BookBorrow borrow = new BookBorrow(cardId, selectedBookId, borrowDate, endDate);
        BorrowBookDAO borrowBookDAO = new BorrowBookDAO();
        borrowBookDAO.insertBorrowB(borrow);
        bookDAO.updateQuantity(selectedBookId, book.getQuantity() - 1);

        // Sau khi mượn thành công, trừ số dư thẻ theo phí mượn
        double newBalance = cusBorrow.getCardValue() - rentalPrice;
        cusBorrow.setCardValue(newBalance);
        cbDao.update(cusBorrow);

        DefaultTableModel borrowModel = (DefaultTableModel) TableBorrow.getModel();
        borrowModel.addRow(new Object[]{selectedBookId, rentalPrice, borrowDate, endDate});
        ReportContent.setText("Bạn đã mượn sách: " + selectedBookTitle + "\nNgày mượn: " + borrowDate + "\nNgày trả: " + endDate);
    }//GEN-LAST:event_BORROWActionPerformed

    private void REPPORTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_REPPORTActionPerformed
        // Kiểm tra đã chọn sách hay chưa
        if (selectedBookId == null || selectedBookId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Vui lòng chọn một cuốn sách để báo cáo!");
            return;
        }

        String reportContent = ReportContent.getText().trim();
        if (reportContent.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Vui lòng nhập nội dung báo cáo!");
            return;
        }

        LocalDate reportDate = LocalDate.now();
        Report report = new Report(null, customerId, selectedBookId, reportDate, reportContent);

        try {
            ReportDAO reportDAO = new ReportDAO();

            // Fix lỗi: Sử dụng RIGHT(reportId, 3) để lấy 3 ký tự cuối cùng thay vì SUBSTRING()
            reportDAO.insert(report);

            ReportContent.setText("");
            JOptionPane.showMessageDialog(this, "✅ Báo cáo thành công cho sách: " + selectedBookTitle);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Đã xảy ra lỗi khi gửi báo cáo. Vui lòng thử lại.");
            ex.printStackTrace();
        }
    }//GEN-LAST:event_REPPORTActionPerformed

    private void PAYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PAYActionPerformed
        // Khởi tạo đối tượng BillDAO
        BillDAO billDAO = new BillDAO();

        Bill.setLineWrap(true);
        Bill.setWrapStyleWord(true);
        Bill.setText(Bill.toString());

        // Gọi phương thức processPayment, truyền customerId của khách hàng hiện tại
        String billText = billDAO.processPayment(customerId);

        // Hiển thị nội dung bill lên textfield Bill
        Bill.setText(billText);

        // Cập nhật lại bảng TableBuy (ví dụ bằng cách gọi phương thức updateTableBuy đã có)
        updateTableBuy();

        // Làm trống bảng TableBorrow (ví dụ bằng cách setRowCount = 0 cho model)
        DefaultTableModel borrowModel = (DefaultTableModel) TableBorrow.getModel();
        borrowModel.setRowCount(0);

        // Thông báo thành công cho người dùng
        JOptionPane.showMessageDialog(this, "Thanh toán thành công. Bill đã được tạo!");
        
        
        // Tính tổng giá trị đơn mua từ bảng TableBuy
        double currentPurchase = calculateCurrentPurchase();

        // Lấy thông tin khách hàng hiện tại từ database
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.getById(customerId);

        if (customer != null) {
            // Tính tổng thanh toán mới: tổng hiện tại cộng với giá trị đơn mua
            double newTotal = customer.getTotalPayment() + currentPurchase;

            // Cập nhật tổng thanh toán mới vào database
            customerDAO.updateTotalPayment(customerId, newTotal);

            // Cập nhật lại đối tượng Customer trong bộ nhớ (nếu cần)
            customer.setTotalPayment(newTotal);

            // Cập nhật giao diện hiển thị thông tin khách hàng
            // Giả sử bạn có giao diện CustomerInfo để hiển thị thông tin của khách hàng
            CustomerInfo customerInfo = new CustomerInfo(customer);
            customerInfo.setCustomer(customer);
            customerInfo.display();

            // Cập nhật nội dung bill (ví dụ hiển thị trên JTextArea Bill)
            Bill.setText("Thanh toán thành công!\n"
                    + "Tổng giá trị đơn mua: " + currentPurchase + "\n"
                    + "Tổng thanh toán mới: " + newTotal);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin khách hàng!");
        }
    }//GEN-LAST:event_PAYActionPerformed

    private void RECHARGEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RECHARGEActionPerformed
        // TODO add your handling code here:
        // Xây dựng cardId theo quy ước 
        String cardId = "Card" + customerId.substring(1);
        CustomerBorrowDAO cbDao = new CustomerBorrowDAO();
        CustomerBorrow cusBorrow = cbDao.getById(cardId);
        if (cusBorrow == null) {
            JOptionPane.showMessageDialog(this, "Bạn chưa có thẻ. Hãy tạo thẻ.");
            return;
        }

        double rechargeAmount;
        try {
            rechargeAmount = Double.parseDouble(QuantityRecharge.getText().trim());
            if (rechargeAmount <= 0) {
                JOptionPane.showMessageDialog(this, "Số tiền nạp phải lớn hơn 0!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền hợp lệ!");
            return;
        }

        // Cộng thêm số tiền nạp vào số dư hiện có
        double newBalance = cusBorrow.getCardValue() + rechargeAmount;
        cusBorrow.setCardValue(newBalance);

        // Cập nhật lại thông tin thẻ trong CSDL
        cbDao.update(cusBorrow);

        JOptionPane.showMessageDialog(this, "Nạp tiền thành công. Số dư mới: " + newBalance);
    }//GEN-LAST:event_RECHARGEActionPerformed

    private void QuantityRechargeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuantityRechargeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_QuantityRechargeActionPerformed

    private void CreateCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateCardActionPerformed
        // TODO add your handling code here:
        CustomerDAO cDao = new CustomerDAO();
        CustomerBorrowDAO cbDao = new CustomerBorrowDAO();

        // Lấy loại thẻ được chọn và xác định giá trị cũng như giới hạn mượn tương ứng
        String typeCard = TypeCard.getSelectedItem().toString();
        int cardValue = 0;
        int borrowLimit = 0;
        if (typeCard.equals("ĐỒNG")) {
            cardValue = 50;
            borrowLimit = 10;
        } else if (typeCard.equals("BẠC")) {
            cardValue = 100;
            borrowLimit = 20;
        } else if (typeCard.equals("VÀNG")) {
            cardValue = 200;
            borrowLimit = 30;
        }

        // Giả sử customerId đã được lưu khi khách hàng đăng nhập
        String cid = customerId;  // Ví dụ: "C001"
        // Xây dựng cardId theo quy ước: "Card" + phần số của Cid (ví dụ: "Card001")
        String cardId = "Card" + cid.substring(1);

        // Thiết lập ngày đăng ký hiện tại và thời hạn hiệu lực thẻ (vd: 1 năm)
        LocalDate registrationDate = LocalDate.now();
        LocalDate cardExpiry = registrationDate.plusMonths(1);

        // Tạo đối tượng CustomerBorrow mới
        CustomerBorrow newCard = new CustomerBorrow(cardId, cid, typeCard, cardExpiry, registrationDate, cardValue, borrowLimit);

        // Thực hiện chèn thẻ vào bảng CustomerBorrow
        boolean success = cbDao.insert(newCard);
        if(success) {
            // Cập nhật lại tổng thanh toán cho khách hàng (nếu có quy định cập nhật theo cardValue)
            cDao.updateTotalPayment(cid, cardValue);

            // Nạp lại dữ liệu từ CSDL bằng cách gọi lại getAll() để cập nhật lại bộ sưu tập card trong DAO
            cbDao.getAll();

            // Cập nhật giao diện: ví dụ hiển thị thông tin thẻ mới tạo ở TextField CardNotification
            Bill.setText("Thẻ: " + cardId + " | Loại: " + typeCard + " | HSD: " + cardExpiry);
            JOptionPane.showMessageDialog(this, "Tạo thẻ thư viện thành công và dữ liệu đã được nạp lại!");
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi khi tạo thẻ thư viện.");
        }
    }//GEN-LAST:event_CreateCardActionPerformed

    private void LogoutMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutMousePressed
        // TODO add your handling code here:
                // TODO add your handling code here:
        // Mở trang Login ban đầu. Giả sử lớp LoginPage là trang đăng nhập.
        new Login().setVisible(true);
        // Đóng cửa sổ CustomerPage hiện tại.
        this.dispose();
    }//GEN-LAST:event_LogoutMousePressed

    private void CusinfoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CusinfoMousePressed
        // Truy vấn lại thông tin khách hàng từ database
        CustomerDAO customerDAO = new CustomerDAO();
        Customer updatedCustomer = customerDAO.findById(customerId);

        if(updatedCustomer == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin khách hàng!");
            return;
        }

        // Tạo cửa sổ CustomerInfo mới với thông tin cập nhật và hiển thị nó
        CustomerInfo cusInfo = new CustomerInfo(updatedCustomer);
        cusInfo.setVisible(true);
    }//GEN-LAST:event_CusinfoMousePressed

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
    }
    
    public static void run(String customerId) {
        java.awt.EventQueue.invokeLater(() -> new CustomerPage(customerId).setVisible(true));
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable AvailableBook;
    private javax.swing.JButton BORROW;
    private javax.swing.JButton BUY;
    private javax.swing.JTextArea Bill;
    private javax.swing.JPanel ChatbotPanel;
    private javax.swing.JButton CreateCard;
    private javax.swing.JLabel Cusinfo;
    private javax.swing.JButton Find;
    private javax.swing.JSpinner InputQuantity;
    private javax.swing.JTextField InputToFind;
    private javax.swing.JLabel Logout;
    private javax.swing.JButton PAY;
    private javax.swing.JTextField QuantityRecharge;
    private javax.swing.JButton RECHARGE;
    private javax.swing.JButton REPPORT;
    private javax.swing.JTextField ReportContent;
    private javax.swing.JTable ResultOfFind;
    private javax.swing.JComboBox<String> SelectedToFind;
    private javax.swing.JTable TableBorrow;
    private javax.swing.JTable TableBuy;
    private javax.swing.JTabbedPane TotalPurchaseAndMakingCard;
    private javax.swing.JComboBox<String> TypeCard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables
}
