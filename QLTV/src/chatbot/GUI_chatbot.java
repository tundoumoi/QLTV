/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package chatbot;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author LENOVO Ideapad 3
 */
public class GUI_chatbot extends javax.swing.JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private final ChatbotController controller;
    
    public GUI_chatbot() {
        super("Chatbot Quản Lý Thư Viện");
        controller = new ChatbotController();

        // Thiết lập vùng hiển thị tin nhắn
        chatArea = new JTextArea(20, 40);
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // Thiết lập ô nhập liệu
        inputField = new JTextField(30);
        sendButton = new JButton("Gửi");

        // Panel chứa input
        JPanel inputPanel = new JPanel();
        inputPanel.add(inputField);
        inputPanel.add(sendButton);

        // Cấu trúc bố cục
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Sự kiện gửi tin nhắn khi nhấn nút hoặc Enter
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processInput();
            }
        });
        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processInput();
            }
        });

        // Cài đặt cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void processInput() {
        String userInput = inputField.getText().trim();
        if (!userInput.isEmpty()) {
            chatArea.append("Bạn: " + userInput + "\n");
            String response = controller.getResponse(userInput);
            chatArea.append("Chatbot: " + response + "\n\n");
            inputField.setText("");
        }
    }

    public static void main(String[] args) {
        new GUI_chatbot();
    }

    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
