package com.company;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private double salary;
    private boolean isManager;
    private int managerId; // nullable
    private boolean isCEO;

    public Employee(){}

    public Employee(int id, String firstName, String lastName, double salary, boolean isManager, int managerId, boolean isCEO) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.isManager = isManager;
        this.managerId = managerId;
        this.isCEO = isCEO;
    }

    public int getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public double getSalary() {
        return salary;
    }
    public int getManagerID() {
        return managerId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setSalary(int rank) {
        // calculate the salary = (salary coefficient) * (user input rank(int between 1-10))
        double salaryCoefficient;
        if (this.isCEO) {
            salaryCoefficient = 2.725;
        }else if(this.isManager){
            salaryCoefficient = 1.725;
        }else{
            salaryCoefficient = 1.125;
        }
        this.salary = rank * salaryCoefficient;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }
    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
    public void setCEO(boolean CEO) {
        isCEO = CEO;
    }
}
