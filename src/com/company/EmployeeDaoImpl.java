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
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    //list is working as a local database
    List<Employee> employees;

    public EmployeeDaoImpl(){
        employees = new ArrayList<Employee>();
    }

    @Override // has to be updated evey now and then in case there is a change in the db from another user running the program the same time.
    public List<Employee> getAllEmployees() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        statement = connection.createStatement();
        resultSet = statement.executeQuery(QueryHelper.getEmployees());
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
    public void createEmployee() throws SQLException {
        Employee employee = new Employee();
        employee.setFirstName(new Scanner(System.in).nextLine());
        employee.setLastName(new Scanner(System.in).nextLine());
        employee.setSalary(InputHelper.getRank());
        String role = InputHelper.getRole("create", employees);
        int managerId = InputHelper.getManagerID(role, employees);
        employee.setRole(role, managerId);
        // upload the employee to db
        // get the id automatically incremented to the new employee

        resultSet = statement.executeQuery(QueryHelper.createEmployee(employee));
        while (resultSet.next()){
            int id = resultSet.getInt("LAST_INSERT_ID()");
            employee.setId(id); // get id from db l8r
        }
        employees.add(employee); // add to local arraylist
    } // create new employee

    @Override
    public void updateEmployee(Employee employee) throws SQLException {
        System.out.print("Please choose a field to update:\n" +
                "1. First name \n" +
                "2. Last name \n" +
                "3. Salary \n" +
                "4. Role \n" +
                "5. EXIT \n" +
                "Enter option number here: ");
        boolean flag = true;
        while(flag) {
            switch (new Scanner(System.in).nextInt()) {
                case 1:
                    employee.setFirstName(new Scanner(System.in).nextLine());
                    statement.execute(QueryHelper.setFirstName(employee));
                    break;
                case 2:
                    employee.setLastName(new Scanner(System.in).nextLine());
                    statement.execute(QueryHelper.setLastName(employee));
                    break;
                case 3:
                    employee.setSalary(InputHelper.getRank());
                    statement.execute(QueryHelper.setSalary(employee));
                    break;
                case 4:
                    String newRole = (InputHelper.getRole("update to", employees));
                    employee.setRole(newRole, InputHelper.getManagerID(newRole, employees));
                    statement.execute(QueryHelper.setIsCEO(employee));
                    statement.execute(QueryHelper.setIsManager(employee));
                    statement.execute(QueryHelper.setManagerId(employee));
                    break;
                case 5:
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again by selecting a number.");
                    break;
            }
        }
    } // update existing employee

    @Override
    public void deleteEmployee(Employee employeeDel) throws SQLException {
        // You should not be able to delete a manger or CEO that is managing another employee
        Employee managerToEmployee = (Employee) employees.stream().filter(employee -> Objects.equals(employee.getManagerID(), employeeDel.getId()));
        if(managerToEmployee!=null){
            System.out.println("The employee you are trying to delete is a manager to someone.");
        }else{
            statement.execute(QueryHelper.deleteEmployee(employeeDel));
        }
    } // delete employee that isn't a boss to anyone
}