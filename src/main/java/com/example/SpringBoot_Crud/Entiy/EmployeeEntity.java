package com.example.SpringBoot_Crud.Entiy;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employees")
public class EmployeeEntity {

    @Id
    private String employeeId; // ✅ Changed from int to String

    private String employeeName;
    private int age;
    private double salary;
    private long mobileNumber;

    // Getters and Setters

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString() {
        return "EmployeeEntity [employeeId=" + employeeId + ", employeeName=" + employeeName + ", age=" + age
                + ", salary=" + salary + ", mobileNumber=" + mobileNumber + "]";
    }

    public EmployeeEntity() {
        super();
    }

    public EmployeeEntity(String employeeId, String employeeName, int age, double salary, long mobileNumber) {
        super();
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.age = age;
        this.salary = salary;
        this.mobileNumber = mobileNumber;
    }

}
