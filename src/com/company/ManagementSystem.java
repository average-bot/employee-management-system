package com.company;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ManagementSystem {
    public static void main(String[] args) throws SQLException {
        EmployeeDao employeeDao = new EmployeeDaoImpl();
        List<Employee> employees = employeeDao.getAllEmployees();

        printEmployees(employees);
        employeeDao.createEmployee();
        /*
        while(true){
            try{
                System.out.print("Please input the id of the employee: ");
                Scanner scanner = new Scanner(System.in);
                int id = scanner.nextInt();

                Employee employee = employeeDao.getEmployee(id);
                // employeeDao.deleteEmployee(employee); // for deleting
                // employeeDao.updateEmployee(employee); // for updating
                employeeDao.createEmployee();
                break;
            }catch (NoSuchElementException exception){
                System.out.println("The employee id is not present. Try again.");
            }
        }
        */

        printEmployees(employees);

        employeeDao.closeConnection();
    }

    public static void printEmployees(List<Employee> employees) throws SQLException {
        for (Employee employee : employees) {
            System.out.println("Employee: [Id : " + employee.getId() + ", Firstname : " + employee.getFirstName() + ", Salary : " + employee.getSalary()+" ]");
        }//print all employees
    }
}
