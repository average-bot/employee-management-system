package com.company;

import java.sql.SQLException;

public class ManagementSystem {

    public static void main(String[] args) throws SQLException {
        EmployeeDao employeeDao = new EmployeeDaoImpl();

        //print all employees
        for (Employee employee : employeeDao.getAllEmployees()) {
            System.out.println("Employee: [Id : " + employee.getId() + ", Firstname : " + employee.getFirstName() + " ]");
        }


        //update employee
        Employee employee = employeeDao.getAllEmployees().get(0);
        employee.setFirstName("Michael");
        employeeDao.updateEmployee(employee);

        //get the employee
        employeeDao.getEmployee(0);
        System.out.println("Employee: [Id : " + employee.getId() + ", Name : " + employee.getFirstName() + " ]");
    }
}
