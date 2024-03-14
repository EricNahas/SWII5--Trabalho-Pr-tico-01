package gerenciador;

import java.util.*;
import java.sql.*;

public class EmpDao {
    public static Connection getConnection() {
        Connection con = null;
        try {
            System.out.println("Getting database connection...");

            String dbDriver = "com.mysql.cj.jdbc.Driver";
            String dbURL = "jdbc:mysql://localhost:3306/sys?useLegacyDatetimeCode=false&serverTimezone=America/Sao_Paulo";
            String dbUsername = "root";
            String dbPassword = "root";

            Class.forName(dbDriver);

            con = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            System.out.println("Connection established successfully.");
        } catch (Exception e) {
            System.out.println("Connection failed: " + e);
        }
        return con;
    }

    public static int save(Emp e) {
        int status = 0;
        try {
            System.out.println("Inserting new employee...");
            Connection con = EmpDao.getConnection();
            PreparedStatement ps = con.prepareStatement("insert into user905(name,password,email,country) values (?,?,?,?)");
            ps.setString(1, e.getName());
            ps.setString(2, e.getPassword());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getCountry());

            status = ps.executeUpdate();
            System.out.println("Inserted new employee successfully.");

            con.close();
        } catch (Exception ex) {
            System.out.println("Failed to insert new employee: " + ex);
            ex.printStackTrace();
        }
        return status;
    }

    public static int update(Emp e) {
        int status = 0;
        try {
            System.out.println("Updating employee...");
            Connection con = EmpDao.getConnection();
            PreparedStatement ps = con.prepareStatement("update user905 set name=?,password=?,email=?,country=? where id=?");
            ps.setString(1, e.getName());
            ps.setString(2, e.getPassword());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getCountry());
            ps.setInt(5, e.getId());

            status = ps.executeUpdate();
            System.out.println("Updated employee successfully.");

            con.close();
        } catch (Exception ex) {
            System.out.println("Failed to update employee: " + ex);
            ex.printStackTrace();
        }

        return status;
    }

    public static int delete(int id) {
        int status = 0;
        try {
            System.out.println("Deleting employee...");
            Connection con = EmpDao.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from user905 where id=?");
            ps.setInt(1, id);

            status = ps.executeUpdate();

            System.out.println("Deleted employee successfully.");

            con.close();
        } catch (Exception e) {
            System.out.println("Failed to delete employee: " + e);
            e.printStackTrace();
        }

        return status;
    }

    public static Emp getEmployeeById(int id) {
        Emp e = new Emp();

        try {
            System.out.println("Fetching employee by ID...");
            Connection con = EmpDao.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from user905 where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                e.setId(rs.getInt(1));
                e.setName(rs.getString(2));
                e.setPassword(rs.getString(3));
                e.setEmail(rs.getString(4));
                e.setCountry(rs.getString(5));
                System.out.println("Fetched employee successfully.");
            }
            con.close();
        } catch (Exception ex) {
            System.out.println("Failed to fetch employee: " + ex);
            ex.printStackTrace();
        }

        return e;
    }

    public static List<Emp> getAllEmployees() {
        List<Emp> list = new ArrayList<Emp>();

        try {
            System.out.println("Fetching all employees...");
            Connection con = EmpDao.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from user905");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Emp e = new Emp();
                e.setId(rs.getInt(1));
                e.setName(rs.getString(2));
                e.setPassword(rs.getString(3));
                e.setEmail(rs.getString(4));
                e.setCountry(rs.getString(5));
                list.add(e);
            }
            System.out.println("Fetched all employees successfully.");
            con.close();
        } catch (Exception e) {
            System.out.println("Failed to fetch employees: " + e);
            e.printStackTrace();
        }

        return list;
    }
}
