package com.company;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class InputHelper {

    public static int getPresentEmployeeId(List<Employee> employees){
        int id;
        while(true){
            try{
                System.out.print("Please input the id of the employee: ");
                Scanner scanner = new Scanner(System.in);
                id = scanner.nextInt();
                int finalId = id;
                boolean employeeExists = employees.stream().anyMatch(employee -> Objects.equals(employee.getId(), finalId));
                if(employeeExists){
                    break;
                }
            }catch (NoSuchElementException exception){
                System.out.println("The employee id is not present. Try again.");
            }
        }return id;
    }

    public static String getValidRole(){
        String potentialRole = "";
        do{
            Scanner scanner = new Scanner(System.in);
            if(scanner.hasNext()) {
                potentialRole = scanner.nextLine();
                potentialRole = potentialRole.toLowerCase();
                if(potentialRole.equals("employee") || potentialRole.equals("manager") || potentialRole.equals("ceo")){
                    break;
                }else{
                    System.out.println("Invalid input. Please choose between employee, manager and CEO.");
                }
            }
        else{ System.out.print("Invalid input. Please input characters: "); }
        } while(true);
        return potentialRole;
    }

    public static int getRank(){
        System.out.println("To select salary for the employee please input their rank.");
        int rank = 0;
        Scanner scanner;
        do{
            scanner = new Scanner(System.in);
            if(scanner.hasNextInt()) {
                rank = scanner.nextInt();
            }else{
                System.out.println("Try again.");
            }
        }while (!((rank > 0) && (rank <= 10)));
        return rank;
    } // get the rank from the user to later decide the salary

    public static int getManagerId(String role, List<Employee> employees) {
        // managers can manage other managers and employee but not CEO
        if (role.equals("ceo")) {
            // CEO has no manager
            return 0;
        }
        // If employee or manager
        int id;
        do {
            id = getPresentEmployeeId(employees);
            int finalId = id;
            Employee potentialManager = employees.stream().filter(employee -> Objects.equals(employee.getId(), finalId)).findAny().get();
            if (potentialManager.getIsCEO() && role.equals("employee")) {
                // CEO cannot manage employees directly
                System.out.println("The CEO cannot manage the employee directly. Please choose a manager instead");
            } else if (potentialManager.getIsManager() || potentialManager.getIsCEO()) {
                //CEO can manage managers, managers can manage managers and employees
                break;
            } else {
                System.out.println("Please choose a manager to manage an employee or manager and CEO to manage the manager.");
            }
        } while (true);
        return id;
    } // get the manager id


    public static String getRole(String operation, List<Employee> employees, int id){
        String potentialRole = "";
        System.out.print("Please enter the type of employee you want to "+ operation +": ");
        do{
            potentialRole = getValidRole();
            if(potentialRole.equals("ceo")){ // check if there is one CEO already (1 max)
                boolean existCEO = employees.stream().anyMatch(employee -> Objects.equals(employee.getIsCEO(), true));
                if(!existCEO){
                    break;
                } else{
                    System.out.println("A CEO already exists in the system, you cannot create another CEO.");
                }
            } else if (potentialRole.equals("employee") && id!=0){
                boolean isManagerToSomeone = employees.stream().anyMatch(employee -> Objects.equals(employee.getManagerID(), id));
                if (isManagerToSomeone){ System.out.println("The person is managing someone already, cannot change the role to employee."); }
            }else{ break; }
        }while(true);
        return potentialRole;
    } // get the available role for..
}
