/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ultils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Admin
 */
public interface IValidation {

    static final String NAME_REGEX = "^[A-Z][a-z0-9]*(\\s[A-Z][a-z0-9]*)*$";
    static final String EMAIL_REGEX = "^[a-zA-Z0-9][a-zA-Z0-9._%+-]*[a-zA-Z0-9]@([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
    static final String PHONE_REGEX = "0\\d{9}";
    static final String ID_CARD_REGEX = "\\d{9}|\\d{12}";
    static final String BIRTH_DATE_REGEX = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
    static final SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("yyyy-MM-dd"); 
    static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-dd-MM");
    static final String GENDER_REGEX = "Male|Female";
    

}
