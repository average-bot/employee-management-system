package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeDaoImpl implements EmployeeDao {
    static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    static final String USER = "rooter";
    static final String PASS = "rooter";
    static final String GET_EMPLOYEES_QUERY = "SELECT * FROM employees";
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    //list is working as a database
    List<Employee> employees;

    public EmployeeDaoImpl(){
        employees = new ArrayList<Employee>();
    }

    @Override
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
        employee.setId(2);
        employee.setFirstName("");
        employee.setLastName("");
        employee.setSalary(22);
        employee.setManager(true); // managers can manage other manages and employee but not CEO
        employee.setManagerId(33);
        employee.setCEO(false); // only one CEO in total + CEO can manage managers but not employees + No one can manage the CEO

        statement.execute(""); // TODO: Insert employee to employees table

        employees.add(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {

    }

    @Override
    public void deleteEmployee(Employee employee) {
        // You should not be able to delete a manger or CEO that is managing another employee

    }
    // TODO : Validation on input fields
}