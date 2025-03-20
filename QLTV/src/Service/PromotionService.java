/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;


import DAO.PromotionDAO;
import Model.Promotion;
/**
 *
 * @author dangt
 */
public class PromotionService {
    private final PromotionDAO promotionDAO = new PromotionDAO();


    public Promotion checkVoucher(double purchaseAmount) {
        return promotionDAO.checkVoucher(purchaseAmount);
    }
}
