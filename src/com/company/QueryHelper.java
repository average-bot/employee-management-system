package com.company;

public class QueryHelper {

    public static String getEmployees() {
        return "SELECT * FROM employee";
    } // get all employees

    public static String createEmployee(Employee employee) {
        return "INSERT INTO `employee` (`Id`, `FirstName`, `LastName`, `Salary`, `isCEO`, `isManager`, `ManagerId`) VALUES (NULL,'"+ employee.getFirstName()+"', '"+employee.getLastName()+"', "+employee.getSalary()+", "+employee.getIsCEO()+", "+employee.getIsManager()+", "+employee.getManagerID()+");SELECT LAST_INSERT_ID();";
    } // create employee + get their id


    // update field
    public static String setFirstName(Employee employee) {
        return "UPDATE employee SET FirstName = '"+employee.getFirstName()+"' WHERE Id = "+ employee.getId() +";";
    }
    public static String setLastName(Employee employee) {
        return "UPDATE employee SET LastName = '"+employee.getLastName()+"' WHERE Id = "+ employee.getId() +";";
    }
    public static String setSalary(Employee employee){
        return "UPDATE employee SET Salary = '"+employee.getSalary()+"' WHERE Id = "+ employee.getId() +";";
    }
    public static String setIsCEO(Employee employee){
        return "UPDATE employee SET isCEO = "+employee.getIsCEO()+" WHERE Id = "+ employee.getId() +";";
    }
    public static String setIsManager(Employee employee){
        return "UPDATE employee SET isManager = "+employee.getIsManager()+" WHERE Id = "+ employee.getId() +";";
    }
    public static String setManagerId(Employee employee){
        return "UPDATE employee SET ManagerId = "+employee.getManagerID()+" WHERE Id = "+ employee.getId() +";";
    }


    public static String deleteEmployee(Employee employee){
        return "DELETE FROM employee WHERE Id="+employee.getId()+";";
    }// delete employee
}
