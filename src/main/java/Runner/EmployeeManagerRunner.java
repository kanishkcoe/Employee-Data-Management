package Runner;

import Model.Employee;

import java.sql.*;
import java.util.Scanner;

public class EmployeeManagerRunner {

    static Scanner scanner = new Scanner(System.in);

    public static Statement statement;
    public static Connection connection;
    public static ResultSet resultSet;

    public static void main(String[] args) throws SQLException {

        System.out.println("Welcome to Employee Data Management System");

        //JDBC configuration
        String url = "jdbc:mysql://localhost:3306/company";
        String username = "root";
        String password = "root";

        try {
            //Load and Register a driver
            Class.forName("com.mysql.jdbc.Driver");

            //establish the connection
            connection = DriverManager.getConnection(url, username, password);

            //build the query
            statement = connection.createStatement();

            int choice;

            do {
                menu();
                System.out.println("Enter your choice : ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        insertEmployee();
                        break;
                    case 2:
                        removeEmployee();
                        break;
                    case 3:
                        updateEmployee();
                        break;
                    case 4:
                        showEmployee();
                        break;
                    case 5:
                        showAllEmployee();
                        break;
                    case 6:
                        System.out.println("quitting program.");
                        break;
                    default:
                        System.out.println("Invalid choice, please select (1 - 6)");
                }
            } while (choice != 6);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            statement.close();
            connection.close();
        }
    }

    private static void showAllEmployee() throws SQLException {
        String query = "select * from employees";

        System.out.println("Running query : " + query);

        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Employee employee = new Employee(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("department"),
                    resultSet.getDouble("salary"));
            System.out.println(employee);
        }

    }

    private static void showEmployee() throws SQLException {
        System.out.println("Enter the Employee ID: ");
        int id = scanner.nextInt();

        String query = "select * from employees where id = " + id;
        System.out.println("Running query : " + query);

        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Employee employee = new Employee(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("department"),
                    resultSet.getDouble("salary"));
            System.out.println(employee);
        }
    }

    private static void updateEmployee() throws SQLException {
        System.out.println("Enter the Employee ID: ");
        int id = scanner.nextInt();

        String query = "select * from employees where id = " + id;
        System.out.println("Running query : " + query);

        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Employee employee = new Employee(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("department"),
                    resultSet.getDouble("salary"));
            System.out.println(employee);
        }

        System.out.println("Enter what do you want to update: ");
        System.out.println("1. Update name");
        System.out.println("2. Update department");
        System.out.println("3. Update salary");

        System.out.println("Enter choice : ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Enter name: ");
                String name = scanner.nextLine();
                query = "update employees set name = '"+ name +"' where id = " + id;
                int count = statement.executeUpdate(query);
                System.out.println(count + " row/s affected.");
                break;
            case 2:
                System.out.println("Enter department: ");
                String department = scanner.nextLine();
                query = "update employees set department = '"+ department +"' where id = " + id;
                count = statement.executeUpdate(query);
                System.out.println(count + " row/s affected.");
                break;
            case 3:
                System.out.println("Enter salary: ");
                double salary = scanner.nextDouble();
                scanner.nextLine();
                query = "update employees set salary = '"+ salary +"' where id = " + id;
                count = statement.executeUpdate(query);
                System.out.println(count + " row/s affected.");
                break;
            default:
                System.out.println("invalid choice.");
        }
    }

    private static void removeEmployee() throws SQLException {
        System.out.println("Enter the Employee ID: ");
        int id = scanner.nextInt();

        String query = "select * from employees where id = " + id;
        System.out.println("Running query : " + query);

        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Employee employee = new Employee(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("department"),
                    resultSet.getDouble("salary"));
            System.out.println(employee);
        }

        System.out.println("Are you sure you want to delete ? (y/n)");
        String confirmation = scanner.next();

        if(confirmation.equals("y")) {
            query = "delete from employees where id = " + id;
            int count = statement.executeUpdate(query);
            System.out.println(count + " row/s affected.");
            System.out.println("Employee successfully data deleted");
        } else {
            System.out.println("Employee data not deleted");
        }
    }

    private static void insertEmployee() throws SQLException {
        System.out.println("Enter name : ");
        String name = scanner.nextLine();

        System.out.println("Enter department : ");
        String department = scanner.nextLine();

        System.out.println("Enter salary : ");
        double salary = scanner.nextDouble();
        scanner.nextLine();

        Employee employee = new Employee(name, department, salary);

        String query = "insert into employees (name, department, salary) value ('" + employee.getName() + "', '" + employee.getDepartment() + "', " + employee.getSalary() + ")";

        System.out.println("Running query : " + query);

        int count = statement.executeUpdate(query);
        System.out.println(count + " row/s affected.");
    }

    private static void menu() {
        System.out.println("1.Insert Employee");
        System.out.println("2.Remove Employee");
        System.out.println("3.UpdateEmployee");
        System.out.println("4.Show Employee");
        System.out.println("5.Show all Employee");
        System.out.println("6. Quit");
    }
}
