package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class EmployeeDaoImpl implements EmployeeDao {    // TODO : Validation on input fields
    static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    static final String USER = "rooter";
    static final String PASS = "rooter";
    static final String GET_EMPLOYEES_QUERY = "SELECT * FROM employee";
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    //list is working as a database
    List<Employee> employees;

    public EmployeeDaoImpl(){
        employees = new ArrayList<Employee>();
    }

    @Override // has to be updated evey now and then incase there is a change in the db from another user running the program the same time.
    public List<Employee> getAllEmployees() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        statement = connection.createStatement();
        resultSet = statement.executeQuery(GET_EMPLOYEES_QUERY);
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            double salary = resultSet.getDouble("salary");
            boolean isManager = resultSet.getBoolean("isManager");
            int managerId = resultSet.getInt("managerId"); // nullable
            boolean isCEO = resultSet.getBoolean("isCEO");
            Employee employee = new Employee(id, firstName, lastName, salary, isManager, managerId, isCEO);
            employees.add(employee);
        }
        connection.close();
        statement.close();
        return employees;
    }

    @Override
    public Employee getEmployee(int id) {
        return (Employee) employees.stream().filter(employee -> Objects.equals(employee.getId(), id));
    }

    @Override
    public void createEmployee() throws SQLException {
        Employee employee = new Employee();
        employee.setFirstName(new Scanner(System.in).nextLine());
        employee.setLastName(new Scanner(System.in).nextLine());
        employee.setSalary(getRank());
        String role = getRole("create");
        int managerId = getManagerID(role);
        employee.setRole(role, managerId);
        // upload the employee to db
        // get the id automatically incremented to the new employee
        resultSet = statement.executeQuery("INSERT INTO `employee` (`Id`, `FirstName`, `LastName`, `Salary`, `isCEO`, `isManager`, `ManagerId`) VALUES (NULL,'"+ employee.getFirstName()+"', '"+employee.getLastName()+"', "+employee.getSalary()+", "+employee.getIsCEO()+", "+employee.getIsManager()+", "+employee.getManagerID()+");SELECT LAST_INSERT_ID();");
        while (resultSet.next()){
            int id = resultSet.getInt("LAST_INSERT_ID()");
            employee.setId(id); // get id from db l8r
        }
        employees.add(employee); // add to local arraylist
    }

    @Override
    public void updateEmployee(Employee employee) {

    }

    @Override
    public void deleteEmployee(Employee id) throws SQLException {
        // You should not be able to delete a manger or CEO that is managing another employee
        Employee managerToEmployee = (Employee) employees.stream().filter(employee -> Objects.equals(employee.getManagerID(), id));
        if(managerToEmployee!=null){
            System.out.println("The employee you are trying to delete is a manager to someone.");
        }else{
            statement.execute("DELETE FROM employee WHERE Id="+id+";");
        }
    }

    private int getRank(){
        int rank = 0;
        Scanner scanner;
        do{
            scanner = new Scanner(System.in);
            if(scanner.hasNextInt()) {
                rank = scanner.nextInt();
            }
        }while ((rank > 0) && (rank <= 10));
        return rank;
    }

    private int getManagerID(String role){
        // managers can manage other managers and employee but not CEO
        if (role.equals("CEO")){
            // CEO has no manager
            return 0;
        }else{
            // If employee or manager
            int id;
            Scanner scanner;
            System.out.print("Please enter the manager's or CEO's id: ");
            do{
                scanner = new Scanner(System.in);
                if(scanner.hasNextInt()) {
                    id = scanner.nextInt();
                    Employee potentialManager = getEmployee(id);
                    if (potentialManager.getIsCEO() && role.equals("employee")){
                        // CEO cannot manage employees directly
                        System.out.println("The CEO cannot manage the employee directly. Please choose a manager instead");
                        continue;
                    } else if(potentialManager.getIsManager() || potentialManager.getIsCEO()){
                        //CEO can manage managers, managers can manage managers and employees
                        break;
                    }else{
                        System.out.println("Please choose a manager to manage an employee or manager and CEO to manage the manager.");
                    }
                }
                System.out.print("Invalid input. Please input numbers: ");
            }while(true);
            return id;
        }
    }


    private String getRole(String operation){
        String potentialRole = "";
        Scanner scanner;
        System.out.print("Please enter the type of employee you want to "+ operation +": ");
        do{
            scanner = new Scanner(System.in);
            if(scanner.hasNext()) {
                potentialRole = scanner.nextLine();
                potentialRole = potentialRole.toLowerCase();
                if(potentialRole.equals("employee") || potentialRole.equals("manager") || potentialRole.equals("CEO")){
                    if(potentialRole.equals("CEO")){ // check if there is one CEO already (1 max)
                        Employee existCEO = (Employee) employees.stream().filter(employee -> Objects.equals(employee.getIsCEO(), true));
                        if(existCEO == null){
                            break;
                        } else{
                            System.out.println("A CEO already exists in the system, you cannot create another CEO.");
                        }
                    } else{ break; }
                }
                System.out.println("Invalid input. Please choose between employee, manager and CEO.");
            }
            System.out.print("Invalid input. Please input characters: ");
        }while(true);
        return potentialRole;
    }
} // TODO: validation!