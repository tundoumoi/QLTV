package DAO;

import Model.Employee;
import comparator.EmployeeComparator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.TreeSet;


public class EmployeeDAO implements IEmployeeDAO {

    private TreeSet<Employee> EList = new TreeSet<>(new EmployeeComparator());

    public EmployeeDAO() {
        EList = getAll();  
    }

    public TreeSet<Employee> getEList() {
        return EList;
    }

    @Override
    public TreeSet<Employee> getAll() {
        String sql = "SELECT * FROM Employee";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("Eid");
                String name = rs.getString("Ename");
                String ssn = rs.getString("Essn");
                String birthDate = rs.getString("EbirthDate");
                String gender = rs.getString("Egender");
                String phoneNumber = rs.getString("Ephone");
                String email = rs.getString("Eemail");
                String address = rs.getString("Eaddress");
                String position = rs.getString("Eposition");
                double salary = rs.getDouble("Esalary");
                LocalDate startDate = LocalDate.parse(rs.getString("EstartDate"));
                String accountId = rs.getString("AccountId");

                Employee emp = new Employee(id, name, ssn, LocalDate.parse(birthDate), gender, phoneNumber, email, address, position, salary, startDate, accountId);
                EList.add(emp); // TreeSet tự loại bỏ trùng
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
        String sql = "INSERT INTO Employee (Eid, Ename, Essn, EbirthDate, Egender, Ephone, Eemail, Eaddress, Eposition, Esalary, EstartDate, AccountId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emp.getId());
            pstmt.setString(2, emp.getName());
            pstmt.setString(3, emp.getId());
            pstmt.setString(4, emp.getBirthDate().toString());
            pstmt.setString(5, emp.getGender());
            pstmt.setString(6, emp.getPhoneNumber());
            pstmt.setString(7, emp.getEmail());
            pstmt.setString(8, emp.getAddress());
            pstmt.setString(9, emp.getPosition());
            pstmt.setDouble(10, emp.getSalary());
            pstmt.setString(11, emp.getStartDate().toString());
            pstmt.setString(12, emp.getAccountId());

            pstmt.executeUpdate();
            EList.add(emp); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  @Override
public void update(Employee emp) { 
    String sql = "UPDATE Employee SET Ename = ?, Essn = ?, EbirthDate = ?, Egender = ?, Ephone = ?, Eemail = ?, Eaddress = ?, Eposition = ?, Esalary = ?, EstartDate = ?, AccountId = ? WHERE Eid = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, emp.getName());
        pstmt.setString(2, emp.getId());
        pstmt.setString(3, emp.getBirthDate().toString());
        pstmt.setString(4, emp.getGender());
        pstmt.setString(5, emp.getPhoneNumber());
        pstmt.setString(6, emp.getEmail());
        pstmt.setString(7, emp.getAddress());
        pstmt.setString(8, emp.getPosition());
        pstmt.setDouble(9, emp.getSalary());
        pstmt.setString(10, emp.getStartDate().toString());
        pstmt.setString(11, emp.getAccountId());
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
  public void updateName(String id, String name) {
    String sql = "UPDATE Employee SET Ename = ? WHERE Eid = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
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

public void updateBirthDate(String id, String birthDate) {
    String sql = "UPDATE Employee SET EbirthDate = ? WHERE Eid = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
    String sql = "UPDATE Employee SET Ephone = ? WHERE Eid = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
    String sql = "UPDATE Employee SET Eemail = ? WHERE Eid = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
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

public void updateAccountId(String id, String accountId) {
    String sql = "UPDATE Employee SET AccountId = ? WHERE Eid = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, accountId);
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
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
