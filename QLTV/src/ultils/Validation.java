package ultils;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validation implements IValidation {

    private static final Scanner sc = new Scanner(System.in);

    public String getValue(String msg, String err) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) {
                return s;
            }
            System.out.println(err);
        }
    }

    public int getInt(String msg, int min, int max) {

        while (true) {
            try {
                System.out.print(msg);
                int n = Integer.parseInt(sc.nextLine().trim());
                if (min <= n && max >= n) {
                    return n;
                }
                System.err.println("Out of range, your number must from:" + min + " to " + max);
            } catch (NumberFormatException ex) {
                System.err.println("Wrong format, please input an integer");
            }
        }
    }

    public double getDouble(String msg, double min, double max) {

        while (true) {
            System.out.print(msg);
            try {
                double n = Double.parseDouble(sc.nextLine().trim());
                if (min <= n && max >= n) {
                    return n;
                }
                System.err.println("Out of range, your number must from:" + min + " to " + max);
            } catch (NumberFormatException ex) {
                System.err.println("Wrong format, please input an real number");
            }
        }
    }

    public long getLong(String msg, long min, long max) {

        while (true) {
            System.out.print(msg);
            try {
                long n = Long.parseLong(sc.nextLine().trim());
                if (min <= n && max >= n) {
                    return n;
                }
                System.err.println("Out of range, your number must from:" + min + " to " + max);
            } catch (NumberFormatException ex) {
                System.err.println("Wrong format, please input an real number");
            }
        }
    }

    public String normalizeName(String name) {
        name = name.replaceAll("[^a-zA-Z ]", "");
        name = name.replaceAll("\\s+", " ");
        String[] words = name.split("\\s+");
        StringBuilder normalized = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                normalized.append(capitalizedWord).append(" ");
            }
        }
        return normalized.toString().trim();
    }

    public boolean getYesNo(String msg) {
        while (true) {
            System.out.printf(msg);
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("Y")) {
                return true;
            } else if (input.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.err.println("Invalid input. Please enter Y or N");
            }
        }
    }

        public String getValidatedInput(String prompt, String errorMessage, String regex) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (Pattern.matches(regex, input)) {
                return input;
            } else {
                System.err.println(errorMessage);
            }
        }
    }

    public boolean isValidDate(String dateStr) {
        DATE_FORMAT1.setLenient(false);
        try {
            DATE_FORMAT1.parse(dateStr);
            return true;
        } catch (ParseException e) {
            System.err.println("this day is not valid!");
            return false;
        }
    }

    public <T> boolean checkDuplicateObject(List<T> list, String value, String methodName) {
        for (T obj : list) {
            try {
                Method method = obj.getClass().getMethod(methodName);
                Object result = method.invoke(obj);
                if (result != null && result.toString().equals(value)) {
                    return true;
                }
            } catch (Exception e) {
                System.err.println(value + " is duplicated!");
            }
        }
        return false;
    }
    

    private int calculateAge(String birthDateStr) {
        try {
            LocalDate birthDate = LocalDate.parse(birthDateStr, DATE_FORMAT);
            LocalDate currentDate = LocalDate.now();

            if (birthDate.isAfter(currentDate)) {
                return 0;
            }

            return Period.between(birthDate, currentDate).getYears();
        } catch (Exception e) {
            return 0;
        }
    }

    public String getBirthDate(String msg, int age) {
        String birthDate;
        do {
            birthDate = getValidatedInput("Enter birth date (DD/MM/YYYY): ", "Invalid birth date format. Please try again.", BIRTH_DATE_REGEX);
            if (!isValidDate(birthDate)) {
                System.err.println("Invalid birth date format. Please try again.");
                birthDate = null;
                continue;
            }
            if (calculateAge(birthDate) < age) {
                System.out.println(msg + " must be at least " + age + " years old.");
                birthDate = null;
            }
        } while (birthDate == null);
        return birthDate;
    }

    public String getId(String msg, String ID_REGEX) {
        String id;
        id = getValidatedInput(msg, "Invalid ID format. Please try again.", ID_REGEX);
        return id;
    }

    public String getGender() {
        String gender = getValidatedInput("Enter gender (Male/Female): ", "Invalid gender. Please try again.", GENDER_REGEX);
        gender = normalizeName(gender);
        return gender;
    }

    public String getID_Card(String msg) {
        String ID_Card = getValidatedInput(msg, "Invalid ID card. Please try again.", ID_CARD_REGEX);
        return ID_Card;
    }

    public String getName(String msg) {
        String name = getValidatedInput( msg , "Wrong form name, pls enter again!", NAME_REGEX);
        return name;
    }
    public String getType(String msg, String REGEX) {
        String type = getValidatedInput("Enter " + msg , "Wrong form type, pls enter again!", REGEX);
        return type;
    }
    public String getPhone(String msg) {
        String phone = getValidatedInput("Enter " + msg + " phone number (0xxxxxxxxx): ", "Invalid phone number. Please try again.", PHONE_REGEX);
        return phone;
    }

    public String getEmail() {
        String Email = getValidatedInput("Enter an email: ", "Invalid email. Please try again.", EMAIL_REGEX);
        return Email;
    }
}
