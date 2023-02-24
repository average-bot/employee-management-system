package com.company;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class InputHelper {

    public static int getRank(){
        System.out.println("To select salary for the employee please input their rank.");
        int rank = 0;
        Scanner scanner;
        do{
            scanner = new Scanner(System.in);
            if(scanner.hasNextInt()) {
                rank = scanner.nextInt();
            }
        }while ((rank > 0) && (rank <= 10));
        return rank;
    } // get the rank from the user to later decide the salary

    public static int getManagerID(String role, List<Employee> employees){
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
                    int finalId = id;
                    Employee potentialManager = (Employee) employees.stream().filter(employee -> Objects.equals(employee.getId(), finalId));
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
    } // get the manager id

    public static String getRole(String operation, List<Employee> employees){
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
    } // get the available role for..

}
