/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Run;

import DAO.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
      String query = "Select * from Admin";
           try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String ADid = rs.getString("ADid");
                String Aname = rs.getString("Aname");
                String Assn = rs.getString("Assn");
                String ADbirthDate = rs.getString("ADbirthDate");
                String ADgender = rs.getString("ADgender");
                String ADphoneNumber = rs.getString("ADphoneNumber");
                String ADemail = rs.getString("ADemail");
                String ADaddress = rs.getString("ADaddress");
                String AccountId = rs.getString("AccountId");
              
                System.out.println(ADid+ Aname+ Assn+ ADbirthDate+ ADgender+ ADphoneNumber+ ADemail+ ADaddress+ AccountId);
            }
        }catch(SQLException e){
               System.out.println(e.getMessage());
    
        }
}
}
