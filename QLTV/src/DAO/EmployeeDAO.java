package DAO;

import Model.Account;
import Model.Employee;
import comparator.EmployeeComparator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.TreeSet;

public class EmployeeDAO implements IEmployeeDAO {

    private TreeSet<Employee> EList = new TreeSet<>(new EmployeeComparator());
    private HashSet<Account> emAcc = new HashSet<>();

    public EmployeeDAO() {
        EList = getAll();
        loadAcc();
    }

    public TreeSet<Employee> getEList() {
        return EList;
    }

    public void loadAcc() {
        String query = "SELECT a.AccountId, a.username , a.APass FROM Account a inner join Employee em on a.AccountId = em.AccountId";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String userName = rs.getString("userName");
                    String APass = rs.getString("APass");
                    int accountId = rs.getInt("AccountId");
                    Account acc = new Account(accountId, userName, APass);
                    emAcc.add(acc);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public HashSet<Account> getEmAcc() {
        emAcc.clear();
        loadAcc();
        return emAcc;
    }

    @Override
    public TreeSet<Employee> getAll() {
        String sql = "SELECT * FROM Employee";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("Eid");
                String name = rs.getString("Ename");
                String ssn = rs.getString("Essn");
                String birthDate = rs.getString("EbirthDate");
                String gender = rs.getString("Egender");
                String phoneNumber = rs.getString("EphoneNumber");
                String email = rs.getString("EDemail");
                String address = rs.getString("Eaddress");
                String position = rs.getString("Eposition");
                double salary = rs.getDouble("Esalary");
                LocalDate startDate = LocalDate.parse(rs.getString("EstartDate"));
                int accountId = rs.getInt("AccountId");

                Employee emp = new Employee(id, name, ssn, LocalDate.parse(birthDate), gender, phoneNumber, email, address, position, salary, startDate, accountId);
                EList.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return EList;
    }

    @Override
    public Employee getById(String id) {
        for (Employee emp : EList) {
            if (emp.getId().equalsIgnoreCase(id)) {
                return emp;
            }
        }
        return null;
    }

    @Override
    public void insert(Employee emp) {
        String sql = "INSERT INTO Employee (Eid, Ename, Essn, EbirthDate, Egender, EphoneNumber, EDemail, Eaddress, Eposition, Esalary, EstartDate, AccountId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emp.getId());
            pstmt.setString(2, emp.getName());
            pstmt.setString(3, emp.getSSN());
            pstmt.setString(4, emp.getBirthDate().toString());
            pstmt.setString(5, emp.getGender());
            pstmt.setString(6, emp.getPhoneNumber());
            pstmt.setString(7, emp.getEmail());
            pstmt.setString(8, emp.getAddress());
            pstmt.setString(9, emp.getPosition());
            pstmt.setDouble(10, emp.getSalary());
            pstmt.setString(11, emp.getStartDate().toString());
            pstmt.setInt(12, emp.getAccountId());

            pstmt.executeUpdate();
            EList.add(emp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Employee emp) {
        String sql = "UPDATE Employee SET Ename = ?, Essn = ?, EbirthDate = ?, Egender = ?, EphoneNumber = ?, EDemail = ?, Eaddress = ?, Eposition = ?, Esalary = ?, EstartDate = ?, AccountId = ? WHERE Eid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emp.getName());
            pstmt.setString(2, emp.getSSN());
            pstmt.setString(3, emp.getBirthDate().toString());
            pstmt.setString(4, emp.getGender());
            pstmt.setString(5, emp.getPhoneNumber());
            pstmt.setString(6, emp.getEmail());
            pstmt.setString(7, emp.getAddress());
            pstmt.setString(8, emp.getPosition());
            pstmt.setDouble(9, emp.getSalary());
            pstmt.setString(10, emp.getStartDate().toString());
            pstmt.setInt(11, emp.getAccountId());
            pstmt.setString(12, emp.getId());
            pstmt.executeUpdate();

            Employee target = null;
            for (Employee e : EList) {
                if (e.getId().equalsIgnoreCase(emp.getId())) {
                    target = e;
                    break;
                }
            }
            if (target != null) {
                EList.remove(target);
            }

            EList.add(emp);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Employee getEmployeeByUsername(String username) {
        String sql = "SELECT Eid, Ename, Essn, EbirthDate, Egender, EphoneNumber, EDemail, Eaddress, Eposition, Esalary, EstartDate, em.AccountId FROM Account acc  JOIN Employee em ON acc.AccountId = em.AccountId WHERE acc.username = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Employee(
                        rs.getString("Eid"),
                        rs.getString("Ename"),
                        rs.getString("Essn"),
                        rs.getObject("EbirthDate", LocalDate.class),
                        rs.getString("Egender"),
                        rs.getString("EphoneNumber"),
                        rs.getString("EDemail"),
                        rs.getString("Eaddress"),
                        rs.getString("Eposition"),
                        rs.getDouble("Esalary"),
                        rs.getObject("EstartDate", LocalDate.class),
                        rs.getInt("AccountId")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Trả về null nếu không tìm thấy nhân viên
    }

    public void updateName(String id, String name) {
        String sql = "UPDATE Employee SET Ename = ? WHERE Eid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Employee emp : EList) {
            if (emp.getId().equalsIgnoreCase(id)) {
                emp.setName(name);
                break;
            }
        }
    }

    public void updateSSN(String id, String ssn) {
        String sql = "UPDATE Employee SET Essn = ? WHERE Eid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ssn);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Employee emp : EList) {
            if (emp.getId().equalsIgnoreCase(id)) {
                emp.setSSN(ssn);
                break;
            }
        }
    }

    public void updateBirthDate(String id, String birthDate) {
        String sql = "UPDATE Employee SET EbirthDate = ? WHERE Eid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, birthDate);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LocalDate date = LocalDate.parse(birthDate);
        for (Employee emp : EList) {
            if (emp.getId().equalsIgnoreCase(id)) {
                emp.setBirthDate(date);
                break;
            }
        }
    }

    public void updateGender(String id, String gender) {
        String sql = "UPDATE Employee SET Egender = ? WHERE Eid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gender);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Employee emp : EList) {
            if (emp.getId().equalsIgnoreCase(id)) {
                emp.setGender(gender);
                break;
            }
        }
    }

    public void updateAddress(String id, String address) {
        String sql = "UPDATE Employee SET Eaddress = ? WHERE Eid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, address);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Employee emp : EList) {
            if (emp.getId().equalsIgnoreCase(id)) {
                emp.setAddress(address);
                break;
            }
        }
    }

    public void updatePhoneNumber(String id, String phoneNumber) {
        String sql = "UPDATE Employee SET EphoneNumber = ? WHERE Eid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, phoneNumber);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Employee emp : EList) {
            if (emp.getId().equalsIgnoreCase(id)) {
                emp.setPhoneNumber(phoneNumber);
                break;
            }
        }
    }

    public void updateEmail(String id, String email) {
        String sql = "UPDATE Employee SET EDemail = ? WHERE Eid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Employee emp : EList) {
            if (emp.getId().equalsIgnoreCase(id)) {
                emp.setEmail(email);
                break;
            }
        }
    }

    public void updatePosition(String id, String position) {
        String sql = "UPDATE Employee SET Eposition = ? WHERE Eid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, position);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Employee emp : EList) {
            if (emp.getId().equalsIgnoreCase(id)) {
                emp.setPosition(position);
                break;
            }
        }
    }

    public void updateSalary(String id, double salary) {
        String sql = "UPDATE Employee SET Esalary = ? WHERE Eid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, salary);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Employee emp : EList) {
            if (emp.getId().equalsIgnoreCase(id)) {
                emp.setSalary(salary);
                break;
            }
        }
    }

    public void updateStartDate(String id, String startDate) {
        String sql = "UPDATE Employee SET EstartDate = ? WHERE Eid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LocalDate date = LocalDate.parse(startDate);
        for (Employee emp : EList) {
            if (emp.getId().equalsIgnoreCase(id)) {
                emp.setStartDate(date);
                break;
            }
        }
    }

    public void updateAccountId(String id, int accountId) {
        String sql = "UPDATE Employee SET AccountId = ? WHERE Eid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Employee emp : EList) {
            if (emp.getId().equalsIgnoreCase(id)) {
                emp.setAccountId(accountId);
                break;
            }
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Employee WHERE Eid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();

            Employee target = null;
            for (Employee emp : EList) {
                if (emp.getId().equalsIgnoreCase(id)) {
                    target = emp;
                    break;
                }
            }
            if (target != null) {
                EList.remove(target);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
