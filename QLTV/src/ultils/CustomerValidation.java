/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ultils;

/**
 *
 * @author NHAT NAM FAN BAC MEOOOOOO S1
 */
public class CustomerValidation implements ICustomerValidation {

    Validation val = new Validation();

    public String getCardId() {
        return val.getId("Enter Card ID(CB-XXX): ", CARD_ID_REGEX);

    }
    public String getCustomerID(){
        return val.getId("Enter Customer ID (C-XXX): ", CUSTOMER_ID_REGEX);
    }
}
