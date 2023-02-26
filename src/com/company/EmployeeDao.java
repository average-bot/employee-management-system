package com.company;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDao {
    public List<Employee> getAllEmployees() throws SQLException;

    public Employee getEmployee(int id);

    public void createEmployee() throws SQLException;

    public void updateEmployee(Employee employee) throws SQLException;

    public void deleteEmployee(Employee employee) throws SQLException;

    public void closeConnection() throws SQLException;

}
