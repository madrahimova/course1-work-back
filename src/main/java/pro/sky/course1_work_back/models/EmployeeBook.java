package pro.sky.course1_work_back.models;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeBook {
    private final Map<Integer, Employee> employees = new HashMap<>();

    public EmployeeBook() {}

    public Map<Integer, Employee> getEmployees() {
        return employees;
    }

    public int getEmployeeCount() {
        return employees.size();
    }

    public int getEmployeeCount(Map<Integer, Employee> employees) {
        return employees.size();
    }

    public int getEmployeeNextId() {
        if (getEmployeeCount() == 0)
            return 0;
        return employees.keySet().stream().max(Integer::compareTo).orElse(-1) + 1;
    }

    public boolean addEmployee(Employee employee) {
        if (!validateDepartment(employee.getDepartment())) return false;
        if (!validateSalary(employee.getSalary())) return false;

        int nextId = getEmployeeNextId();
        employees.put(nextId, employee);
        return true;
    }

    public boolean deleteEmployee(int id) {
        if (!employees.containsKey(id)) return false;

        employees.remove(id);
        return true;
    }

    public boolean alterEmployee(int id, Employee employee) {
        if (!validateDepartment(employee.getDepartment())) return false;
        if (!validateSalary(employee.getSalary())) return false;
        if (!employees.containsKey(id)) return false;

        employees.put(id, employee);
        return true;
    }

    public double getSalarySum() {
        double salarySum = 0;

        for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
            salarySum += entry.getValue().getSalary();
        }

        return salarySum;
    }

    public double getSalarySum(Map<Integer, Employee> employees) {
        double salarySum = 0;

        for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
            salarySum += entry.getValue().getSalary();
        }

        return salarySum;
    }

    public double getAverageSalary() {
        if (getEmployeeCount() == 0) return 0;
        return getSalarySum() / getEmployeeCount();
    }

    public double getAverageSalary(Map<Integer, Employee> employees) {
        if (getEmployeeCount(employees) == 0) return 0;
        return getSalarySum(employees) / getEmployeeCount(employees);
    }

    public Employee getEmployeeWithMinSalary() {
        double minSalary = Double.MAX_VALUE;
        int id = 0;

        for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
            double salary = entry.getValue().getSalary();
            if (salary < minSalary) {
                minSalary = salary;
                id = entry.getKey();
            }
        }

        return employees.get(id);
    }

    public Employee getEmployeeWithMaxSalary() {
        double maxSalary = 0;
        int id = 0;

        for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
            double salary = entry.getValue().getSalary();
            if (salary > maxSalary) {
                maxSalary = salary;
                id = entry.getKey();
            }
        }

        return employees.get(id);
    }

    public boolean indexSalaries(double percent) {
        if (!validateIndexPercent(percent)) return false;

        for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
            Employee employee = entry.getValue();
            double salary = employee.getSalary();
            double salaryDelta = salary * percent / 100;
            salary += salaryDelta;

            if (!validateSalary(salary)) continue;
            employee.setSalary(salary);
        }

        return true;
    }

    public Map<Integer, Employee> getEmployeesInDepartment(int department) {
        return employees.entrySet()
                .stream()
                .filter(item -> item.getValue().getDepartment() == department)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public double getSalarySumInDepartment(int department) {
        if (!validateDepartment(department)) return -1;

        Map<Integer, Employee> employeesInDepartment = getEmployeesInDepartment(department);
        return getSalarySum(employeesInDepartment);
    }

    public double getAverageSalaryInDepartment(int department) {
        if (!validateDepartment(department)) return -1;

        Map<Integer, Employee> employeesInDepartment = getEmployeesInDepartment(department);
        return getAverageSalary(employeesInDepartment);
    }

    private boolean validateDepartment(int department) {
        return department >= 1 && department <= 5;
    }

    private boolean validateSalary(double salary) {
        return salary >= 0 && salary <= Double.MAX_VALUE;
    }

    private boolean validateIndexPercent(double percent) {
        return percent >= 0 && percent <= Double.MAX_VALUE;
    }
}
