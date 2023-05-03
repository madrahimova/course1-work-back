package pro.sky.course1_work_back.models;

public class Employee {
    private static int ID_COUNTER = 0;
    private final String fullName;
    private int id;
    private int department;

    private double salary;

    public Employee(String fullName, int department, int salary) {
        setId();
        this.fullName = fullName;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId() {
        id = ID_COUNTER;
        ID_COUNTER++;
    }

    public String getFullName() {
        return fullName;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
